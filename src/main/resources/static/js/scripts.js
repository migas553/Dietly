/*!
* Start Bootstrap - Modern Business v5.0.7 (https://startbootstrap.com/template-overviews/modern-business)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-modern-business/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project




/*
Scripts from user page
 */

// Meat slider, no pun intended
const meatSlider = document.getElementById('Protein');
const meatOutput = document.getElementById('MeatOut');
const fishOutput = document.getElementById('FishOut');

// Get the hidden input fields
const fishValue = document.getElementById('fishValue');
const meatValue = document.getElementById('meatValue');
// Set default values
meatSlider.value = 50;
fishValue.value = 50;
meatValue.value = 50;
meatOutput.value = '50%';
fishOutput.value = '50%';

meatSlider.oninput = function() {
    const sliderValue = this.value;
    let meatPercentage, fishPercentage;

    if (sliderValue <= 50) {
        meatPercentage = 100 - sliderValue;
        fishPercentage = sliderValue;
    } else {
        meatPercentage = 100- sliderValue;
        fishPercentage = sliderValue;
    }

    meatOutput.value = meatPercentage + '%';
    fishOutput.value = fishPercentage + '%';

    // Update the hidden input fields
    fishValue.value = fishPercentage;
    meatValue.value = meatPercentage;
}

// Vegetarian Slider
const vegSlider = document.getElementById('Vegetarian');
const vegOutput = document.getElementById('VegetarianOut');

vegSlider.oninput = function() {
    vegOutput.value = this.value + '%';
}

// Image preview
function previewImage(event) {
    var reader = new FileReader();
    reader.onload = function(){
        var output = document.getElementById('imagePreview');
        output.src = reader.result;
        output.style.display = "block";
    };
    reader.readAsDataURL(event.target.files[0]);
}


