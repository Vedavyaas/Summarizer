# Summarizer

**AI-Powered Text Summarization Tool**

---

## 📅 Latest Update: August 18

---

## 📖 Overview

**Summarizer** is a lightweight web application that leverages the **Ollama AI platform (with LLaMA 3)** and a **Java Spring Boot backend** to transform long pieces of text into concise, meaningful summaries.  

The frontend provides a clean and simple interface where users can paste or type text and instantly receive a summary highlighting the most important details.

---

## ✨ Features

🔹 AI-powered text summarization using **LLaMA 3 via Ollama**  
🔹 Reliable **Java Spring Boot backend API**  
🔹 Minimal, user-friendly **HTML/CSS/JavaScript frontend**  
🔹 Extracts and summarizes **descriptions,...**  
🔹 🚀 Planned feature: **WhatsApp group message integration**  

---

## 📦 Prerequisites

- **Java Development Kit (JDK) 17+**  
- **Maven 3.8+**  
- **Ollama installed** with LLaMA 3 model downloaded locally  
- **Docker** (optional, if running Ollama in Docker)  
- **Git**  
- **Twilio account** with WhatsApp Sandbox enabled (for WhatsApp integration)  

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/Vedavyaas/Summarizer.git
cd Summarizer
```
### 2️⃣ Backend Setup


Navigate to the backend folder:

```bash
cd Summarizer/Backend-Summarizer
```
Build and run the Spring Boot backend.


The backend will start at:


http://localhost:8001



### 3️⃣ Ollama & LLaMA 3 Setup


Install Ollama and pull the LLaMA 3 model:

```bash
ollama pull llama3:latest
```


Ensure Ollama is running locally and the model is available.


### 4️⃣ Usage
Open the application in your browser:


```bash
http://localhost:8001
```
Paste or type any long text or event description.


Click Summarize.


A concise summary will appear below.


### 5️⃣ WhatsApp Integration


Create a Twilio account and configure credentials (Account SID and Auth Token are available in your Twilio console). Once logged in, you can use the WhatsApp integration feature.


###📲 WhatsApp Integration Setup


Create a Twilio Account & Enable WhatsApp Sandbox


Sign up or log in to Twilio.


Go to Messaging → Try it Out → Send a WhatsApp message.


Follow the sandbox activation steps (send the given code from your WhatsApp to the sandbox number).


Expose Your Backend Publicly with ngrok


Install ngrok:


brew install ngrok/ngrok/ngrok   # for macOS/Linux


Start a tunnel:

```bash
ngrok http 8001
```
Copy the HTTPS forwarding URL shown (e.g., https://abc123.ngrok.io).


Configure Twilio Webhook URL


In Twilio Console → WhatsApp Sandbox settings, set "When a message comes in" to:

```bash
https://abc123.ngrok.io/twilio/webhook  (POST)
```

Now your ready to Create an Account in the Summarizer.


🚀 Future Improvements


🌍 Multi-language summarization support


🎨 Advanced summarization styles and options


💻 Improved UI/UX


📱 WhatsApp group message summarization (planned)


✨ With Summarizer, you can quickly condense lengthy event details or descriptions into clear, actionable insights — powered by LLaMA 3 and Spring Boot.
