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
  const total = parseInt(__ENV.TOTAL || 3000)
  const host = __ENV.HOST || "localhost"
  console.log(`Gerando ${total} links...`)
  console.log(`Usando o host ${host} ...`)

  for(let i = 0; i < total; i++){
  	resultados.push(http.post(`http://${host}:8080/api/encurta`,payload,params).json());
  }

  return { redirects : resultados };
}

export default function (data) {
  const size = data.redirects.length
  const index = parseInt(Math.random() * size)
  const idSelecionado = data.redirects[index].id
  
	

  const params = {
      'redirects': 0    
  };	
	
  http.get(`http://${__ENV.HOST || "localhost"}:8080/`+idSelecionado,params)	
  sleep(1);
}