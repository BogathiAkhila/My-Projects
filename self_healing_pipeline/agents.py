import os
from google import genai
from google.genai import types

# Set the API key directly in the script environment to bypass terminal configuration issues
os.environ["GEMINI_API_KEY"] = "PASTE_YOUR_ACTUAL_GEMINI_API_KEY_HERE"

# Initialize the Gemini Client
client = genai.Client()

def call_debugging_agent(broken_code: str, error_log: str) -> str:
    """
    Acts as an AI Principal Engineer analyzing logs and rewriting corrected code.
    """
    system_instruction = (
        "You are an automated, elite AI Software Engineer in a CI/CD pipeline.\n"
        "Your task is to analyze the provided Python code and its corresponding execution error.\n"
        "Fix the bugs completely. You must output ONLY the executable Python code block. "
        "Do NOT include any markdown code blocks (like ```python), explanations, or notes. "
        "Your entire response will be fed directly back into a compiler."
    )
    
    prompt = f"""
    --- BROKEN CODE ---
    {broken_code}
    
    --- RUNTIME ERROR LOG ---
    {error_log}
    
    Fix the code above so it executes with exit code 0.
    """
    
    try:
        response = client.models.generate_content(
            model='gemini-2.5-flash',
            contents=prompt,
            config=types.GenerateContentConfig(
                system_instruction=system_instruction,
                temperature=0.2, # Kept low for deterministic, accurate code fixes
            )
        )
        
        # Strip away any accidental markdown wrapping if the model includes it
        cleaned_code = response.text.strip()
        if cleaned_code.startswith("```python"):
            cleaned_code = cleaned_code[9:]
        if cleaned_code.endswith("```"):
            cleaned_code = cleaned_code[:-3]
            
        return cleaned_code.strip()
        
    except Exception as e:
        # Fallback string if an API connection error occurs during the hackathon demo
        return f"# Pipeline Error: Unable to contact Gemini API.\n# Details: {str(e)}"