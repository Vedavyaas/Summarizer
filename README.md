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
- Twilio account with WhatsApp sandbox enabled (for WhatsApp integration)
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

## WhatsApp Integration Setup

1. **Create a Twilio Account and Enable WhatsApp Sandbox**  
   - Sign up or log in to Twilio.  
   - Navigate to **Messaging → Try it Out → Send a WhatsApp message** and follow the sandbox setup steps.  
   - Send the provided code from your personal WhatsApp number to the sandbox number to activate.

2. **Expose Your Spring Boot Backend Publicly using ngrok**  
   - If not already installed, install ngrok:
     ```bash
     brew install ngrok/ngrok/ngrok
     ```
   - Start the tunnel:
     ```bash
     ngrok http 8001
     ```
   - Copy the HTTPS forwarding URL shown (e.g., `https://abc123.ngrok.io`).

3. **Configure Twilio Webhook URL**  
   - Go to the **WhatsApp Sandbox settings** in your Twilio Console.  
   - In the field **"WHEN A MESSAGE COMES IN"**, paste:
     ```
     https://abc123.ngrok.io/twilio/webhook
     ```
   - This allows your backend to receive incoming WhatsApp messages.

4. **Set Twilio Credentials in the Backend**  
   Add the following to your `application.properties`:
   ```properties
   twilio.sid=your_twilio_account_sid
   twilio.auth.token=your_twilio_auth_token
   ```
   Replace the placeholders with your actual Twilio credentials from the Twilio Console.

---


### Troubleshooting:


```bash
500 Internal Server Error:
```


Ensure Ollama service is running and accessible at the URL specified in application.yml.


Git push errors:


If your remote repository has existing commits, pull changes first:


```bash
git pull --rebase origin main
```


Model not found:


Make sure you have pulled the LLaMA 2 model correctly with Ollama.


WhatsApp Messages Not Received:


Verify Twilio webhook URLs and WhatsApp Sandbox configuration. Use ngrok or a similar tool to expose your local backend.



### Future Improvements:


Multi-language support


User authentication and input privacy


Enhanced summarization options and UI improvements


WhatsApp group message summarization integration (planned feature)


---
