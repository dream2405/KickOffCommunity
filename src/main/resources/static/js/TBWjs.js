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
    
    // 기존 선택된 날짜를 추적하는 변수
    let selectedDayElement = null;
    
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
                    if (selectedDayElement) {
                        selectedDayElement.style.backgroundColor = ''; // 이전에 선택된 날짜의 색상 초기화
                    }
                    dayElement.style.backgroundColor = 'darkgreen'; // 선택된 날짜의 색상 변경
                    selectedDayElement = dayElement; // 선택된 날짜 업데이트
                });
            } else {
                dayElement.classList.add('available');
                dayElement.classList.add('clickable');
                dayElement.addEventListener('click', () => {
                    displayAvailableTime();
                    if (selectedDayElement) {
                        selectedDayElement.style.backgroundColor = ''; // 이전에 선택된 날짜의 색상 초기화
                    }
                    dayElement.style.backgroundColor = 'darkgreen'; // 선택된 날짜의 색상 변경
                    selectedDayElement = dayElement; // 선택된 날짜 업데이트
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

function validateForm(event) {
    event.preventDefault();

    const dateInput = document.querySelector('input[name="date"]');
    const reservedTimeInput = document.querySelector('select[name="reservedtime"]');
    const locationInput = document.querySelector('select[name="location"]');
    
    const maintextInput = document.querySelector('textarea[name="maintext"]');
    const teamAInput = document.querySelector('input[name="teamA"]'); //teamB는 참여하기에서 추가

    const date = dateInput.value;
    const reservedTime = reservedTimeInput.value;
    const location = locationInput.value;
    const maintext = maintextInput.value;
    const teamA = teamAInput.value;

    if (!date || !reservedTime || !location|| !maintext || !teamA) {
        alert("모든 내용을 입력해주세요.");
        return false;
    }

    fetch('/api/tennis')
        .then(response => response.json())
        .then(data => {
            const isAlreadyReserved = data.some(item => item.date === date && item.reservedtime === parseInt(reservedTime) && item.location === location);
            if (isAlreadyReserved) {
                alert('이미 예약된 시간입니다.');
            } else {
                // 팀명이 존재하는지 확인하는 로직 추가
                fetch('/tennis/api/teams')
                    .then(response => response.json())
                    .then(teamDatas => {
                        const teamExists = teamDatas.some(team => team.name === teamA);
                        if (teamExists) {
                            document.getElementById("tennisForm").submit();
                        } else {
                            alert('올바른 팀명을 입력하세요.');
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching team data:', error);
                        alert('오류가 발생했습니다. 나중에 다시 시도해주세요.');
                    });
            }
        })
        .catch(error => {
            console.error('Error fetching data:', error);
            alert('오류가 발생했습니다. 나중에 다시 시도해주세요.');
        });
    return false; // 이벤트 핸들러에서 항상 false를 반환하여 폼이 제출되지 않도록 합니다.
}