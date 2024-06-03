// 예약 가능한 시간대
const availableTimes = [7, 9, 11, 13, 15, 17, 19, 21];

// 현재 연도, 월, 일
const currentDate = new Date();
let currentYear = currentDate.getFullYear();
let currentMonth = currentDate.getMonth();
let today = currentDate.getDate();

// 캘린더 생성
document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/tennis')
        .then(response => response.json())
        .then(data => {
            displayCurrentMonthYear(currentYear, currentMonth);
            createCalendar(data, currentYear, currentMonth);
            updateAvailability(today, data);
        })
        
        .catch(error => console.error('Error fetching data:', error));
});

// 현재 연도와 월을 표시하는 함수
function displayCurrentMonthYear(year, month) {
    const monthNames = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];
    document.getElementById('currentMonthYear').textContent = `${year}년 ${monthNames[month]}`;
}

function createCalendar(data, year, month) {
    const calendar = document.querySelector('.calendar');
    calendar.innerHTML = ''; // 이전 달력 내용 지우기
    // 현재 달의 첫 날
    const firstDayOfMonth = new Date(year, month, 1);
    // 현재 달의 마지막 날
    const lastDayOfMonth = new Date(year, month + 1, 0).getDate();
    // 현재 달의 첫 날이 포함된 주의 첫 날
    const firstDayOfWeek = firstDayOfMonth.getDay();

    // 현재 선택된 구장
    const location = document.getElementById('location').value;
    
    // 캘린더에 날짜 추가
    for (let i = 0; i < firstDayOfWeek; i++) {
        const dayElement = document.createElement('div');
        dayElement.classList.add('day');
        dayElement.classList.add('disabled');
        calendar.appendChild(dayElement);
    }
    for (let i = 1; i <= lastDayOfMonth; i++) {
        const date = new Date(year, month, i);
        const dateString = date.toISOString().split('T')[0];
        const reservedData = data.filter(item => item.date === dateString && item.location === location);
        const dayElement = document.createElement('div');
        dayElement.classList.add('day');
        dayElement.textContent = `${month + 1}/${i}`; // 월/일 형태로 표시
        // 예약 여부에 따라 클래스 추가
        if (date >= currentDate) {
            if (reservedData.length > 0) {
                dayElement.classList.add('booked');
                dayElement.classList.add('clickable');
                dayElement.addEventListener('click', () => {
                    displayReservedTime(reservedData);
                });
            } else {
                dayElement.classList.add('available');
                dayElement.classList.add('clickable');
                dayElement.addEventListener('click', () => {
                    displayAvailableTime();
                });
            }
        } else {
            dayElement.classList.add('disabled');
        }
        calendar.appendChild(dayElement);
    }
}

// 예약 가능 여부 업데이트 함수
function updateAvailability(today, data) {
    const calendarDays = document.querySelectorAll('.day');
    calendarDays.forEach(day => {
        const dayNumber = parseInt(day.textContent.split('/')[1]);
        if (dayNumber < today) {
            day.classList.add('disabled');
        }
    });
}

// 예약 가능한 시간대 표시 함수
function displayAvailableTime() {
    const reservedTimesContainer = document.getElementById('reservedTimes');
    reservedTimesContainer.innerHTML = ''; // 이전에 추가된 정보 지우기

    const availableTimeText = document.createElement('p');
    availableTimeText.textContent = '예약 가능한 시간대:';

    availableTimes.forEach(time => {
        const timeText = document.createElement('span');
        timeText.textContent = `${time}시 `;
        availableTimeText.appendChild(timeText);
    });

    reservedTimesContainer.appendChild(availableTimeText);
}

// 예약된 시간대 표시 함수
function displayReservedTime(reservedData) {
    const reservedTimesContainer = document.getElementById('reservedTimes');
    reservedTimesContainer.innerHTML = ''; // 이전에 추가된 정보 지우기

    const availableTimeText = document.createElement('p');
    availableTimeText.textContent = '예약 가능한 시간대:';

    availableTimes.forEach(time => {
        if (!reservedData.some(item => item.reservedtime === time)) {
            const timeText = document.createElement('span');
            timeText.textContent = `${time}시 `;
            availableTimeText.appendChild(timeText);
        }
    });

    reservedTimesContainer.appendChild(availableTimeText);
}
// 이전 달로 이동
function prevMonth() {
    if (currentMonth === 0) {
        currentYear--;
        currentMonth = 11;
    } else {
        currentMonth--;
    }
    updateCalendar();
}

// 다음 달로 이동
function nextMonth() {
    if (currentMonth === 11) {
        currentYear++;
        currentMonth = 0;
    } else {
        currentMonth++;
    }
    updateCalendar();
}

// 캘린더 업데이트 함수
function updateCalendar() {
    const calendar = document.querySelector('.calendar');
    calendar.innerHTML = ''; // 기존 캘린더 내용 지우기
    displayCurrentMonthYear(currentYear, currentMonth);
    fetch('/api/tennis')
        .then(response => response.json())
        .then(data => {
            createCalendar(data, currentYear, currentMonth);
            updateAvailability(today, data);
        })
        .catch(error => console.error('Error fetching data:', error));
}

//모든 값을 입력해야 글 생성 가능
document.getElementById("tennisForm").addEventListener("submit", function(event) {
    const dateInput = document.querySelector('input[name="date"]');
    const reservedTimeInput = document.querySelector('select[name="reservedtime"]');
    const sportInput = document.querySelector('input[name="sport"]');
    const maintextInput = document.querySelector('textarea[name="maintext"]');
    const teamAInput = document.querySelector('input[name="teamA"]'); //teamB는 참여하기에서 추가

    if (!dateInput.value || !reservedTimeInput.value || !sportInput.value  || !maintextInput.value || !teamAInput.value) {
        alert("모든 내용을 입력해주세요.");
        event.preventDefault(); // 페이지 이동을 막음
    }
});