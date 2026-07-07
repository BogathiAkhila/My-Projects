const apiKey = 'bea59f5430a70d003f383c8f60adcd1c';

// UI Control Layout Elements
const startBtn = document.getElementById('startBtn');
const introContainer = document.getElementById('introContainer');
const mainInterface = document.getElementById('mainInterface');
const searchBtn = document.getElementById('searchBtn');
const cityInput = document.getElementById('cityInput');
const weatherCard = document.getElementById('weatherCard');
const message = document.getElementById('message');

// Dynamic Display DOM Nodes
const bgBody = document.getElementById('bgBody');
const cityName = document.getElementById('cityName');
const description = document.getElementById('description');
const animalContainer = document.getElementById('animalContainer');
const temperature = document.getElementById('temperature');
const suggestionText = document.getElementById('suggestionText');

// INTERACTION 1: Click Intro Button to transition to the form
startBtn.addEventListener('click', () => {
    introContainer.classList.add('opacity-0', 'scale-90');
    
    setTimeout(() => {
        introContainer.classList.add('hidden');
        mainInterface.classList.remove('hidden');
        
        setTimeout(() => {
            mainInterface.classList.remove('opacity-0');
            mainInterface.classList.add('opacity-100');
            cityInput.focus();
        }, 50);
    }, 500);
});

// INTERACTION 2: Trigger Weather Fetching
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

    showStatus('Scanning real-time atmospheric data...');
    weatherCard.classList.add('hidden');

    const url = `https://api.openweathermap.org/data/2.5/weather?q=${encodeURIComponent(city)}&appid=${apiKey}&units=metric`;

    try {
        const response = await fetch(url);
        
        if (!response.ok) {
            if (response.status === 404) throw new Error('City not found. Check spelling!');
            else throw new Error('Something went wrong. Try again.');
        }

        const data = await response.json();
        const temp = Math.round(data.main.temp);
        const condition = data.weather[0].main; // e.g., "Rain", "Clear", "Clouds", "Snow"
        
        // Populate display elements via DOM
        cityName.textContent = `${data.name}, ${data.sys.country}`;
        description.textContent = data.weather[0].description;
        temperature.textContent = temp;

        // INTERACTION 3: Pass both temperature and explicit weather type to update backgrounds
        updateWeatherTheme(temp, condition);

        // Fade outputs smoothly into view
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

/**
 * Direct mapping engine translating weather text data and temperature values
 * into dedicated, contextually specific photography backgrounds.
 */
function updateWeatherTheme(temp, condition) {
    let searchKeyword = "weather";

    // 1. Evaluate specific atmospheric conditions first (Rain, Clouds, Thunderstorm, Snow)
    switch(condition.toLowerCase()) {
        case "rain":
        case "drizzle":
            searchKeyword = "rainy-day,wet-street";
            animalContainer.textContent = "🐸"; // Frog loves rain
            suggestionText.textContent = "It is raining outside! Grab a large umbrella, step safely around puddles, and put on waterproof shoes.";
            break;
            
        case "thunderstorm":
            searchKeyword = "lightning,stormy-sky";
            animalContainer.textContent = "🙀"; // Scared cat
            suggestionText.textContent = "Heavy thunderstorms approaching! Better stay indoors, unplug sensitive electronics, and listen to the thunder comfortably.";
            break;
            
        case "snow":
            searchKeyword = "snowy-forest,blizzard";
            animalContainer.textContent = "☃️";
            suggestionText.textContent = "Beautiful snow is falling! Zip up a thick coat, put on gloves, and maybe construct a snow castle.";
            break;
            
        case "clouds":
        case "mist":
        case "fog":
        case "haze":
            searchKeyword = "foggy-landscape,cloudy-sky";
            animalContainer.textContent = "🐼"; // Chilled panda
            suggestionText.textContent = "Overcast or misty skies ahead. Visibility might be low, but it's a wonderfully peaceful mood for a warm coffee.";
            break;
            
        // 2. Fall back to temperature brackets if condition is clear/standard
        default:
            if (temp <= 15) {
                searchKeyword = "chilly-autumn,cold-morning";
                animalContainer.textContent = "🥶"; 
                suggestionText.textContent = "Brrr, it is freezing cold! Your companion recommends wrapping into a warm blanket and hugging a hot beverage.";
            } else if (temp > 15 && temp <= 28) {
                searchKeyword = "sunny-park,spring-nature";
                animalContainer.textContent = "🦊"; 
                suggestionText.textContent = "The ambient temperature is perfect! Your companion suggests heading out for a stroll or sitting in nature.";
            } else {
                searchKeyword = "desert-heat,scorching-sun"; 
                animalContainer.textContent = "🥵"; 
                suggestionText.textContent = "Whew! It is exceptionally hot. Turn on air conditioning, stay fully hydrated, and seek deep shade.";
            }
            break;
    }

    // A random number parameter forces the browser to discard cache and load a new landscape every click
    const cacheBuster = Math.floor(Math.random() * 9000);
    const dynamicImageURL = `https://images.unsplash.com/featured/1600x900/?${encodeURIComponent(searchKeyword)}&sig=${cacheBuster}`;

    // Apply the image dynamically onto the body element with a clear overlay vignette filter
    bgBody.style.backgroundImage = `linear-gradient(rgba(15, 23, 42, 0.55), rgba(15, 23, 42, 0.75)), url('${dynamicImageURL}')`;
}