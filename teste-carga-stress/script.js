import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
  const payload = JSON.stringify({
    link: 'http://www.teste.com.br'
  });	

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };
 
  http.post('http://localhost:8080/api/encurta',payload,params);
  sleep(1);
}