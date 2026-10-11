import pandas as pd
import mysql.connector
from dotenv import load_dotenv
import os

load_dotenv()

conn = mysql.connector.connect(
    host=os.getenv("DB_HOST"),
    port=int(os.getenv("DB_PORT")),
    user=os.getenv("DB_USER"),
    password=os.getenv("DB_PASSWORD"),
    database=os.getenv("DB_NAME"),
    use_pure=True
)

query = "SELECT ticker, price_date, adj_close FROM daily_prices WHERE ticker IN ('IONQ', 'RGTI', 'QBTS', 'QUBT')"
prices = pd.read_sql(query, conn)
conn.close()

print(prices.head())
print(prices.shape)

wide = prices.pivot(index="price_date", columns="ticker", values="adj_close")
returns = wide.pct_change().dropna() 

expected_returns = returns.mean() 
covariance = returns.cov() 

print(expected_returns)
print(covariance)

from qiskit_finance.applications.optimization import PortfolioOptimization
mu = expected_returns.to_numpy()
sigma = covariance.to_numpy() 
portfolio = PortfolioOptimization(
    expected_returns=mu,
    covariances=sigma,
    risk_factor=0.5,
    budget=2, 
)

qp = portfolio.to_quadratic_program() 
print(qp.prettyprint())

from qiskit_algorithms import QAOA, NumPyMinimumEigensolver 
from qiskit_optimization.algorithms import MinimumEigenOptimizer 

exact_solver = MinimumEigenOptimizer(NumPyMinimumEigensolver())
exact_result = exact_solver.solve(qp)
print(exact_result)
print(portfolio.interpret(exact_result.x))

from qiskit_algorithms import QAOA 
from qiskit_algorithms.optimizers import COBYLA 
from qiskit.primitives import StatevectorSampler 

qaoa = QAOA(sampler=StatevectorSampler(), optimizer=COBYLA(maxiter=250))
qaoa_solver = MinimumEigenOptimizer(qaoa) 
qaoa_result = qaoa_solver.solve(qp)

print(qaoa_result)
print(portfolio.interpret(qaoa_result.x)
      )

split = int(len(returns) * 0.8) 
train = returns.iloc[:split]
test = returns.iloc[split:]


mu_train = train.mean().to_numpy()
sigma_train = train.cov().to_numpy() 

portfolio_train = PortfolioOptimization( 
    expected_returns = mu_train, 
    covariances=sigma_train, 
    risk_factor=0.5, 
    budget =2 
)
qp_train = portfolio_train.to_quadratic_program()
result_train = exact_solver.solve(qp_train)
picked_idx = portfolio_train.interpret(result_train.x)
picked = list(train.columns[picked_idx])

print("Picked from training data:", picked)

picked_growth = (1 + test[picked].mean(axis=1)).cumprod() 
all_growth = (1+test.mean(axis=1)).cumprod()

print("Growth of picked assets:", picked_growth.iloc[-1])
print("Equal-weight all 4 on test data:", all_growth.iloc[-1])

qaoa_result_train = qaoa_solver.solve(qp_train)
qaoa_picked_idx = portfolio_train.interpret(qaoa_result_train.x)
qaoa_picked = list(train.columns[qaoa_picked_idx])

print("QAOA picked from training data:", qaoa_picked)
print("Classical picked from training data:", picked)