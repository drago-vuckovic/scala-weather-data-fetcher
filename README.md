# Weather Data Scala Project

This Scala project leverages the OpenWeatherMap API to retrieve real-time weather data. The program fetches information such as temperature, humidity, and sunrise/sunset times based on specified coordinates and performs data transformations. The transformed data is then stored in a CSV file for convenient analysis.

## Prerequisites

Before running the project, make sure you have:

Scala installed on your machine.
Set up the OpenWeatherMap API key as an environment variable named apiKey.

## Getting Started

- Clone the repository:

```bash
git clone https://github.com/your-username/your-repository.git
cd your-repository
```

- Set your OpenWeatherMap API key as an environment variable:

```bash
export apiKey=your_openweathermap_api_key
```

- Run the Scala project:

```bash
sbt run
```

## Data Transformations

The project performs the following data transformations:

- Conversion of raw weather data into a structured Scala model.
- Formatting of dates using the specified DateTimeFormatter.
- CSV file generation with transformed data columns.

## Generated Output

The project generates a CSV file named output.csv with transformed data, making it suitable for easy analysis and visualization.

## Notes

- The project uses the Circe library for JSON parsing and the OpenCSV library for CSV writing.
- Ensure that your OpenWeatherMap API key is correctly set in the apiKey environment variable.
