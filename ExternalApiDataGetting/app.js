const apiKey = 'bea59f5430a70d003f383c8f60adcd1c';

const searchBtn = document.getElementById('searchBtn');
const cityInput = document.getElementById('cityInput');
const weatherCard = document.getElementById('weatherCard');
const message = document.getElementById('message');

// DOM Elements to change dynamically
const bgBody = document.getElementById('bgBody');
const cityName = document.getElementById('cityName');
const description = document.getElementById('description');
const animalContainer = document.getElementById('animalContainer');
const temperature = document.getElementById('temperature');
const suggestionText = document.getElementById('suggestionText');

searchBtn.addEventListener('click', fetchWeather);
cityInput.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') fetchWeather();
});

async function fetchWeather() {
    const city = cityInput.value.trim();
    
    if (!city) {
        showStatus('Please type a city name first!');
        return;
    }

    showStatus('Locating your buddy...');
    weatherCard.classList.add('hidden');

    const url = `https://api.openweathermap.org/data/2.5/weather?q=${encodeURIComponent(city)}&appid=${apiKey}&units=metric`;

    try {
        const response = await fetch(url);
        
        if (!response.ok) {
            if (response.status === 404) throw new Error('City not found. Check spelling!');
            else throw new Error('Something went wrong.');
        }

        const data = await response.json();
        const temp = Math.round(data.main.temp);
        
        // Update basic weather info via DOM
        cityName.textContent = `${data.name}, ${data.sys.country}`;
        description.textContent = data.weather[0].description;
        temperature.textContent = temp;

        // Trigger UI theme, mascot changes, and recommendations based on temperature
        updateWeatherTheme(temp);

        // Reveal the updated card layout
        message.classList.add('hidden');
        weatherCard.classList.remove('hidden');

    } catch (error) {
        showStatus(error.message);
    }
}

function showStatus(text) {
    message.textContent = text;
    message.classList.remove('hidden');
}

// core function to change layout, animal, background dynamically
function updateWeatherTheme(temp) {
    if (temp <= 15) {
        // Freezing / Cold weather
        bgBody.className = "bg-gradient-to-br from-blue-600 via-indigo-900 to-slate-900 min-h-screen flex items-center justify-center p-4 transition-colors duration-700 font-sans";
        animalContainer.textContent = "🥶"; // Shivering face / Polar bear setup
        suggestionText.textContent = "It's freezing! Wrap yourself in a cozy blanket, grab a steaming mug of hot cocoa, and stay indoors if you can.";
        animalContainer.className = "w-40 h-40 mx-auto rounded-full bg-blue-500/20 border-2 border-blue-400/40 flex items-center justify-center text-7xl shadow-inner animate-animal transition-all duration-500 shadow-blue-500/30";
    
    } else if (temp > 15 && temp <= 28) {
        // Perfect / Pleasant weather
        bgBody.className = "bg-gradient-to-br from-emerald-500 via-teal-800 to-slate-900 min-h-screen flex items-center justify-center p-4 transition-colors duration-700 font-sans";
        animalContainer.textContent = "🦊"; // Happy active fox
        suggestionText.textContent = "The weather is absolutely amazing! Perfect time to go for a walk in the park, touch some grass, or read a book outside.";
        animalContainer.className = "w-40 h-40 mx-auto rounded-full bg-emerald-500/20 border-2 border-emerald-400/40 flex items-center justify-center text-7xl shadow-inner animate-animal transition-all duration-500 shadow-emerald-500/30";
        
    } else {
        // Scorching Hot weather
        bgBody.className = "bg-gradient-to-br from-amber-500 via-orange-800 to-slate-900 min-h-screen flex items-center justify-center p-4 transition-colors duration-700 font-sans";
        animalContainer.textContent = "🥵"; // Panting / melting face
        suggestionText.textContent = "Whew, it's scorching hot out there! Keep the AC running, drink plenty of cold water, and definitely put on sunscreen if you step out.";
        animalContainer.className = "w-40 h-40 mx-auto rounded-full bg-amber-500/20 border-2 border-amber-400/40 flex items-center justify-center text-7xl shadow-inner animate-animal transition-all duration-500 shadow-amber-500/30";
    }
}