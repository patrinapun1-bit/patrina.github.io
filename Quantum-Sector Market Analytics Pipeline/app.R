library(shiny)
library(ggplot2)
library(dplyr)
library(zoo)
library(DBI)
library(RMariaDB)
library(dotenv)
if (file.exists(".env")) load_dot_env()

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
dbDisconnect(con)

prices <- prices |>
  left_join(tickers, by = "ticker") |>
  group_by(ticker) |>
  arrange(price_date) |>
  mutate(rolling_avg = rollmean(close, k = 7, fill = NA, align = "right")) |>
  mutate(daily_return = close / lag(close) - 1) |>
  mutate(rolling_vol = rollapply(daily_return, width = 7, FUN = sd, fill = NA, align = "right"))

ui <- fluidPage(
  titlePanel("Quantum-Sector Market Analytics"),
  selectInput("tickers", "Select tickers:",
              choices = unique(prices$ticker),
              selected = unique(prices$ticker),
              multiple = TRUE),
  plotOutput("vol_plot"),
  tableOutput("summary_tbl")
)

server <- function(input, output) {
  output$vol_plot <- renderPlot({
    filtered <- prices |> filter(ticker %in% input$tickers)
    ggplot(filtered, aes(x = price_date, y = rolling_vol, color = ticker)) +
      geom_line() +
      labs(title = "7-Day Rolling Volatility", x = "Date", y = "Volatility")
  })

  output$summary_tbl <- renderTable({
    prices |>
      filter(ticker %in% input$tickers) |>
      group_by(ticker, sector) |>
      summarise(
        avg_daily_return = mean(daily_return, na.rm = TRUE),
        avg_volatility = mean(rolling_vol, na.rm = TRUE),
        .groups = "drop"
      ) |>
            arrange(desc(avg_volatility))
  }, digits = 4)
}

shinyApp(ui, server)