library(dplyr)
library(ggplot2)
library(zoo)
library(DBI)
library(RMariaDB)
library(dotenv)
library(forecast)
load_dot_env()


  con <- dbConnect(
  RMariaDB::MariaDB(),
  host = Sys.getenv("DB_HOST"),
  port = as.integer(Sys.getenv("DB_PORT")),
  user = Sys.getenv("DB_USER"),
  password = Sys.getenv("DB_PASSWORD"),
  dbname = Sys.getenv("DB_NAME"),
  ssl.ca = "aiven.pem"
)
prices <- dbGetQuery(con, "SELECT * FROM daily_prices")
tickers <- dbGetQuery(con, "SELECT * FROM tickers")

prices <- prices |>
  left_join(tickers, by = "ticker") |>
  group_by(ticker) |>
  arrange(price_date) |>
  mutate(rolling_avg = rollmean(close, k = 7, fill = NA, align = "right")) |>
  mutate(daily_return = close / lag(close) - 1) |>
  mutate(rolling_vol = rollapply(daily_return, width = 7, FUN = sd, fill = NA, align = "right"))

summary_table <- prices |> 
  group_by(ticker, sector) |> 
  summarise( 
    avg_daily_return = mean(daily_return, na.rm = TRUE), 
    avg_volatility = mean(rolling_vol, na.rm = TRUE)) |> 
    arrange(desc(avg_volatility))

    print(summary_table)
    
  p <- ggplot(prices, aes(x = price_date, y = rolling_vol, color = ticker)) +
  geom_line() +
  labs(title = "7-Day Rolling Volatility: Quantum Stocks vs. Benchmarks",
       x = "Date", y = "Volatility")

print(p)
ggsave("volatility_chart.png", plot = p, width = 10, height=6) 

ionq <-prices |> filter(ticker == "IONQ")
fit <- auto.arima(ionq$close) 
fc <- forecast(fit, h =30)
print(fc)

dbDisconnect(con)