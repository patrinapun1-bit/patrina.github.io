import yfinance as yf
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
cursor = conn.cursor()

tickers = ["IONQ", "RGTI", "QBTS", "QUBT", "QTUM", "SPY"]

for ticker in tickers:
    print(f"Pulling {ticker}...")
    data = yf.download(ticker, period="1y", auto_adjust=False, multi_level_index=False)
    data["pct_change"] = data["Adj Close"].pct_change()

    for date, row in data.iterrows():
        if abs(row["pct_change"]) > 0.20:
            log_query = """
                INSERT INTO data_quality_log (ticker, price_date, issue_type, note)
                VALUES (%s, %s, %s, %s)
            """
            cursor.execute(log_query, (
                ticker,
                date.date(),
                "price_spike",
                "Daily price change exceeded 20% threshold"
            ))

        insert_query = """
            INSERT INTO daily_prices (ticker, price_date, open, high, low, close, adj_close, volume)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
            ON DUPLICATE KEY UPDATE
                open = VALUES(open), high = VALUES(high), low = VALUES(low),
                close = VALUES(close), adj_close = VALUES(adj_close), volume = VALUES(volume)
        """
        cursor.execute(insert_query, (
            ticker,
            date.date(),
            float(row["Open"]),
            float(row["High"]),
            float(row["Low"]),
            float(row["Close"]),
            float(row["Adj Close"]),
            int(row["Volume"])
        ))

conn.commit()

cursor.execute("SELECT COUNT(*) FROM daily_prices WHERE ticker = 'IONQ';")
print("Total IONQ rows in database:", cursor.fetchone()[0])

conn.close()

