import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
  vus: 10, // virtual users
  duration: '30s', // test 30 sec
};

export default function () {
  const userId = Math.floor(Math.random() * 10) + 1; // userId 1-10
  const res = http.get(`http://localhost:30080/api/accounts/${userId}`);
  console.log(`Response body: ${res.body}`);
  check(res, {
    'status is 200': (r) => r.status === 200,
    'body is not empty': (r) => r.body.length > 0,
  });

  // Optional: log output (print to terminal only once for demo)
  if (__ITER === 0) {
    console.log(`Fetched userId=${userId}, response=${res.body}`);
  }

  sleep(1);
}
