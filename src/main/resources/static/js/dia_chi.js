// Chọn địa chỉ
const citySelect = document.getElementById("city");
const districtSelect = document.getElementById("district");
const wardSelect = document.getElementById("ward");

let allData = [];

axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json")
    .then(res => {
        allData = res.data;
        renderCities();

        const selectedCity = citySelect.getAttribute("data-selected");
        const selectedDistrict = districtSelect.getAttribute("data-selected");
        const selectedWard = wardSelect.getAttribute("data-selected");

        if (selectedCity) {
            citySelect.value = selectedCity;
            renderDistricts(selectedCity);
            if (selectedDistrict) {
                districtSelect.value = selectedDistrict;
                renderWards(selectedCity, selectedDistrict);
                if (selectedWard) {
                    wardSelect.value = selectedWard;
                }
            }
        }
    });

function renderCities() {
    citySelect.length = 1;
    allData.forEach(city => {
        const cleanedName = cleanName(city.Name);
        citySelect.options[citySelect.options.length] = new Option(cleanedName, cleanedName);
    });
}

function renderDistricts(cityName) {
    const city = allData.find(c => cleanName(c.Name) === cityName);
    districtSelect.length = 1;
    wardSelect.length = 1;
    if (!city) return;

    city.Districts.forEach(dist => {
        const cleanedDist = cleanName(dist.Name);
        districtSelect.options[districtSelect.options.length] = new Option(cleanedDist, cleanedDist);
    });
}

function renderWards(cityName, districtName) {
    const city = allData.find(c => cleanName(c.Name) === cityName);
    if (!city) return;
    const district = city.Districts.find(d => cleanName(d.Name) === districtName);
    wardSelect.length = 1;
    if (!district) return;

    district.Wards.forEach(ward => {
        const cleanedWard = cleanName(ward.Name);
        wardSelect.options[wardSelect.options.length] = new Option(cleanedWard, cleanedWard);
    });
}

function cleanName(name) {
    return name.replace(/^(Tỉnh|Thành phố|Quận|Huyện|Thị xã|Phường|Xã|Thị trấn)\s*/i, '').trim();
}

citySelect.addEventListener("change", () => {
    const city = citySelect.value;
    renderDistricts(city);
});

districtSelect.addEventListener("change", () => {
    const city = citySelect.value;
    const district = districtSelect.value;
    renderWards(city, district);
});
