import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  vus: 10,         // concurrent virtual users 
  duration: '30s', // test 30 sec
};

export default function () {
  let res = http.get('http://localhost:30080/api/health/debug-hostname');
  console.log(`Response body: ${res.body}`);
  check(res, {
    'status is 200': (r) => r.status === 200,
  });

  sleep(1);
}