# Zeotap Assignment – High Throughput Fan-Out Engine

## Overview
This project implements a High Throughput Fan-Out Engine in Java that reads large CSV/JSONL files, transforms records, and delivers them to multiple sinks in parallel with reliability, rate limiting, and retry mechanisms.

The system is designed to simulate real-world backend data pipelines similar to Kafka consumers, ETL pipelines, and distributed ingestion systems.

---

## Features

• Streaming large files without loading into memory  
• Transformation pipeline using Strategy Pattern  
• Fan-out delivery to multiple sinks  
• Parallel processing using Thread Pool  
• Per-sink rate limiting  
• Retry on failure with backoff  
• Throughput metrics and monitoring  
• Clean modular architecture  

---

## Architecture

### 1. File Reader
Reads large CSV files line-by-line using BufferedReader to avoid memory overload.

### 2. Transformer Layer
Uses Strategy Pattern to allow different transformations.
Examples:
- UpperCaseTransformer
- AddPrefixTransformer

### 3. Fan-Out Engine
Each record is sent to multiple sinks in parallel using ExecutorService.

### 4. Sink Layer
Mock sinks simulate real systems:
- ConsoleSink (fast)
- SlowSink (simulates external API)

### 5. Reliability
Two wrapper sinks are implemented:
- RateLimitedSink → controls throughput per sink
- RetrySink → retries failed operations

### 6. Metrics
Program prints:
- Total records processed
- Time taken
- Throughput (records/sec)

---

## How to Run

1. Go to project folder

cd fanout-engine

2. Compile project

mvn clean compile

3. Run program

mvn exec:java "-Dexec.mainClass=com.zeotap.App"

---

## Project Structure

src/main/java/com/zeotap

App.java  
Transformer.java  
UpperCaseTransformer.java  
AddPrefixTransformer.java  
Sink.java  
ConsoleSink.java  
SlowSink.java  
RateLimitedSink.java  
RetrySink.java  

sample.csv → Example input file  

---

## Example Workflow

1. Read record from CSV  
2. Apply transformation  
3. Send record to multiple sinks  
4. Handle failures with retry  
5. Respect rate limits  
6. Track throughput  

---

## Bonus Features Implemented

• Parallel fan-out processing  
• Retry logic for failures  
• Rate limiting per sink  
• Throughput monitoring  
• Clean modular design  

---

## Author

Maneesh Kumar  
Software Engineer Intern Assignment – Zeotap
