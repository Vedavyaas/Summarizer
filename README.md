# Summarizer

**AI-Powered Text Summarization Tool**

---

## 📅 Latest Update: September 5

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

- **Twilio account** with WhatsApp Sandbox enabled (for WhatsApp integration)  

---

## ⚙️ Setup Instructions

### Usage
Open the application in your browser:


```bash
https://summarizer-uhwl.onrender.com
```
Paste or type any long text or event description.


Click Summarize.


A concise summary will appear below.


### WhatsApp Integration


Create a Twilio account and configure credentials (Account SID and Auth Token are available in your Twilio console). Once logged in, you can use the WhatsApp integration feature.


### 📲 WhatsApp Integration Setup


Create a Twilio Account & Enable WhatsApp Sandbox


Sign up or log in to Twilio.


Go to Messaging → Try it Out → Send a WhatsApp message.


Follow the sandbox activation steps (send the given code from your WhatsApp to the sandbox number).

Configure Twilio Webhook URL


In Twilio Console → WhatsApp Sandbox settings, set "When a message comes in" to:

```bash
https://summarizer-uhwl.onrender.com/twilio/webhook  (POST)
```

Now your ready to Create an Account in the Summarizer.




🚀 Future Improvements


🌍 Multi-language summarization support


🎨 Advanced summarization styles and options


💻 Improved UI/UX


📱 WhatsApp group message summarization (planned)




✨ With Summarizer, you can quickly condense lengthy event details or descriptions into clear, actionable insights — powered by LLaMA 3 and Spring Boot.
