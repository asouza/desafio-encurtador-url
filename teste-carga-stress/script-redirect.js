import http from 'k6/http';
import { sleep,group } from 'k6';


export function setup() {
  const payload = JSON.stringify({
    link: 'http://www.teste.com.br'
  });	

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };
 
  const resultados = []
  for(let i = 0; i < 3000; i++){
  	resultados.push(http.post(`http://${__ENV.HOST}:8080/api/encurta`,payload,params).json());
  }

  return { redirects : resultados };
}

export default function (data) {
  const size = data.redirects.length
  const index = parseInt(Math.random() * size)
  const idSelecionado = data.redirects[index].id
  //console.log("Id selecionado = "+idSelecionado)
	

  const params = {
      'redirects': 0    
  };	
	
  http.get(`http://${__ENV.HOST}:8080/`+idSelecionado,params)	
  sleep(1);
}