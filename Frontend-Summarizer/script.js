async function summarize() {
  const inputText = document.getElementById("inputText");
  const summarizeBtn = document.getElementById("summarizeBtn");
  const inputArea = document.getElementById("input-area");
  const loading = document.getElementById("loading");
  const outputArea = document.getElementById("output-area");
  const output = document.getElementById("output");

  const text = inputText.value.trim();
  if (!text) {
    alert("Please enter some text to summarize!");
    return;
  }

  // Hide input, show loading
  inputArea.style.display = "none";
  loading.classList.remove("hidden");
  outputArea.classList.add("hidden");

  try {
    const response = await fetch(`http://localhost:8001/control/api?messages=${encodeURIComponent(text)}`);

    if (!response.ok) throw new Error(`Server error: ${response.status}`);

    const result = await response.text();

    loading.classList.add("hidden");
    output.textContent = result;
    outputArea.classList.remove("hidden");
  } catch (err) {
    loading.classList.add("hidden");
    alert("Failed to get summary: " + err.message);
    // Show input area again for retry
    inputArea.style.display = "block";
  }
}

function goBack() {
  const inputArea = document.getElementById("input-area");
  const outputArea = document.getElementById("output-area");
  const inputText = document.getElementById("inputText");

  outputArea.classList.add("hidden");
  inputArea.style.display = "block";
  inputText.value = "";
}
