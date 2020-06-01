import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 100,
  duration: '10s',
};

const randomStrGen = () => Math.random().toString(36).substring(2);
const randomPhoneNumGen = () => {
  const phoneNum = Math.floor(1000000000 + Math.random() * 9000000000).toString();
  return phoneNum.substring(0, 3) + '-' + phoneNum.substring(3, 6) + '-' + phoneNum.substring(6)
}
const randomZipNoGen = () => {
  return Math.floor(10000 + Math.random() * 90000).toString();
}

export default function() {
  const url = 'http://localhost:8080/v1/customers';
  const body = JSON.stringify({
    "name": randomStrGen(),
    "email": `${randomStrGen()}@test.com`,
    "phone": `${randomPhoneNumGen()}`,
    "address": {
      "zipNo": `${randomZipNoGen()}`,
      "roadAddrPart1": `${randomStrGen()}`,
      "roadAddrPart2": `${randomStrGen()}`,
      "addrDetail": `${randomStrGen()}`,
    },
    "loginId": randomStrGen(),
    "password": randomStrGen()
  });
  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };
  const res = http.post(url, body, params);
  check(res, {
    'status was 200': r => r.status === 200,
    'tx time OK': r => r.timings.duration < 1000
  });
}

/*
k6 설치: https://k6.io/docs/getting-started/installation
실행: k6 run SCRIPT.js
 */
