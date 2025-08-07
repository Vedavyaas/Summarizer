# Summarizer

**AI-Powered Text Summarizer**

---


## Latest Update : August,7


## Overview

Summarizer is a lightweight web application that uses the Ollama AI platform (with LLaMA 2) and a Java Spring Boot backend to transform lengthy descriptions into concise, essential summaries. The frontend provides a simple and clean interface to input event text and receive a quick summary highlighting the most relevant details.

---

## Features

- Natural Language Processing using LLaMA 2 via Ollama
- Robust Java Spring Boot backend API
- Clean HTML/CSS/JavaScript frontend
- Summarizes event details including name, date, venue, contacts, and brief description
- Planned future feature: WhatsApp message summarization integration
---

## Prerequisites

- Java Development Kit (JDK) 17 or above
- Maven 3.8+
- Ollama installed and LLaMA 2 model pulled locally
- Docker (optional, if using Docker for Ollama)
- Git
---

## Setup Instructions

 1. Clone the repository

```bash
git clone https://github.com/your-username/Summarizer.git
cd Summarizer
```
2. Backend Setup


Navigate to the backend folder ( Backend-Summarizer):


```bash
cd Summarizer/Backend-Summarizer
```


Build and run the Spring Boot backend


The backend should now be running at http://localhost:8001.


4. Ollama & LLaMA 2 Setup

   
Download and install Ollama


Pull the LLaMA 2 model:


```bash
ollama pull llama2:latest
```


Make sure Ollama is running locally, and the model is ready.


5. Frontend Setup

   
Navigate to the frontend folder (Frontend-Summarizer):


```bash
cd Summarizer/Frontend-Summarizer
```


Open index.html directly in a browser.


6. Usage

   
Open the Home page.


Paste or type a lengthy event description into the input box.


Click Summarize.


The concise summary will appear below.


# Troubleshooting:


```bash
500 Internal Server Error:
```


Ensure Ollama service is running and accessible at the URL specified in your application.properties.


Git push errors:


If your remote repository has existing commits, pull changes first:


```bash
git pull --rebase origin main
```


Model not found:


Make sure you have pulled the LLaMA 2 model correctly with Ollama.


# Future Improvements:


Multi-language support


User authentication and input privacy


Enhanced summarization options and UI improvements


WhatsApp message summarization integration (planned feature)


---
