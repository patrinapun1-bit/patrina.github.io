import mysql.connector
from dotenv import load_dotenv
import os

load_dotenv()

print("Connecting...")

conn = mysql.connector.connect(
    host=os.getenv("DB_HOST"),
    port=int(os.getenv("DB_PORT")),
    user=os.getenv("DB_USER"),
    password=os.getenv("DB_PASSWORD"),
    database=os.getenv("DB_NAME"),
    use_pure=True
)

print("Connected!")

cursor = conn.cursor()
cursor.execute("SHOW TABLES;")
for table in cursor:
    print(table)

conn.close()