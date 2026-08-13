USE defaultdb;

CREATE TABLE tickers ( 
	ticker VARCHAR(10) PRIMARY KEY, 
    company_name VARCHAR(100), 
    sector VARCHAR(50), 
    is_benchmark BOOLEAN DEFAULT FALSE);
CREATE TABLE daily_prices ( 
	id INT AUTO_INCREMENT PRIMARY KEY, 
    ticker VARCHAR(10), 
    price_date DATE, 
    open DECIMAL (10,2), 
    high DECIMAL(10,2),
    low DECIMAL(10,2), 
    close DECIMAL(10,2), 
    adj_close DECIMAL(10,2), 
    volume BIGINT, 
    FOREIGN KEY (ticker) REFERENCES tickers(ticker), 
    UNIQUE KEY unique_ticker_date(ticker, price_date)
    
    );
    
    CREATE TABLE data_quality_log( 
		id INT AUTO_INCREMENT PRIMARY KEY, 
        ticker VARCHAR(10), 
        price_date DATE, 
        issue_type VARCHAR(50), 
        note TEXT, 
        resolved BOOLEAN DEFAULT FALSE, 
        FOREIGN KEY(ticker) REFERENCES tickers(ticker) 
        
        ); 
        
        INSERT INTO tickers (ticker, company_name, sector, is_benchmark) VALUES 
        ('IONQ', 'IonQ Inc', 'Quantum Computing', FALSE),
        ('RGTI', 'Rigetti Computing', 'Quantum Computing', FALSE),
        ('QBTS', 'D-Wave Quantum', 'Quantum Computing', FALSE), 
        ('QUBT', 'Quantum Computing Inc', 'Quantum Computing', FALSE), 
        ('QTUM', 'Defiance Quantum ETF', 'Benchmark', TRUE),
        ('SPY', 'SPDR S&P 500 ETF', 'Benchmakr', True); 
        
        SHOW TABLES;
	    SELECT * FROM data_quality_log;
        