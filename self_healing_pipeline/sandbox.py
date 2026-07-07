import subprocess
import sys

def execute_code_safely(code_string: str) -> dict:
    """
    Executes a string of code in an isolated subprocess.
    Returns a dictionary with success status and logs/errors.
    """
    try:
        # Run the code string using the current system's Python executable
        result = subprocess.run(
            [sys.executable, "-c", code_string],
            capture_output=True,
            text=True,
            timeout=5 # Prevent infinite loops in broken code
        )
        
        if result.returncode == 0:
            return {
                "success": True,
                "output": result.stdout,
                "error": ""
            }
        else:
            return {
                "success": False,
                "output": result.stdout,
                "error": result.stderr
            }
            
    except subprocess.TimeoutExpired:
        return {
            "success": False,
            "output": "",
            "error": "Timeout Error: Code execution took longer than 5 seconds."
        }