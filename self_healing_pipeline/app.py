import streamlit as st
import time
from sandbox import execute_code_safely
from agents import call_debugging_agent

st.set_page_config(page_title="Self-Healing AI Pipeline", layout="wide")

st.title("⚙️ Self-Healing CI/CD Agentic Engine")
st.caption("This system catches production runtime crashes, analyzes tracebacks, and writes patches autonomously.")

# Columns for UI Layout
col1, col2 = st.columns(2)

with col1:
    st.subheader("Production Code Deployment State")
    # Simulate an application with logic bugs (e.g., mixing types and zero-division risk)
    initial_broken_code = st.text_area(
        "Current Repository State (Edit this to break things!):",
        value="""def calculate_metrics(values):\n    print("Starting processing...")\n    # BUG 1: Appending a string to an integer addition sequence\n    total = sum(values) + "0"\n    # BUG 2: Zero division vulnerability if array is empty\n    average = total / len(values)\n    return average\n\nprint(calculate_metrics([10, 20, 30]))""",
        height=250
    )
    
    trigger_pipeline = st.button("🚀 Push Code to Production / Trigger Simulation")

with col2:
    st.subheader("Agentic Orchestration Logs")
    log_terminal = st.empty()
    status_box = st.empty()

if trigger_pipeline:
    current_code = initial_broken_code
    max_iterations = 4
    iteration = 1
    pipeline_success = False
    
    logs_output = "⚡ Submitting code to automated sandbox...\n"
    log_terminal.code(logs_output)
    
    while iteration <= max_iterations:
        status_box.info(f"Running Validation Loop: Attempt {iteration}/{max_iterations}")
        time.sleep(1) # Artificial latency so judges can watch the live sequence
        
        # 1. Run code inside sandbox
        result = execute_code_safely(current_code)
        
        if result["success"]:
            logs_output += f"\n✅ SUCCESS (Attempt {iteration}): Compilation & execution clean!\nOutput:\n{result['output']}\n"
            log_terminal.code(logs_output)
            status_box.success("🎉 Pipeline Healed Successfully! Preparing production deployment link...")
            pipeline_success = True
            break
        else:
            logs_output += f"\n❌ CRASH DETECTED (Attempt {iteration}):\n{result['error']}\n"
            logs_output += "🤖 Calling AI Principal Engineer Agent to analyze and patch code...\n"
            log_terminal.code(logs_output)
            
            # 2. Call the agent loop to repair code using context details
            patched_code = call_debugging_agent(current_code, result["error"])
            current_code = patched_code
            iteration += 1
            
    if not pipeline_success:
        status_box.error("❌ Pipeline Failed: Could not resolve bugs within maximum execution limit.")
    else:
        # Generate the deployment mock link for production verification
        st.balloons()
        st.markdown("---")
        st.subheader("🌐 Deployed Production Target")
        st.success("Application is live at: `https://agent-healed-pipeline.render.com`")
        with st.expander("Review Deployed Production Source Code"):
            st.code(current_code, language="python")