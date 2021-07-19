import { apiResquest } from '../utils/http.js'

//POST 请求案例

// export const login = (query) => {
// 	return apiResquest({
// 		url: '这里是API的地址',
// 		method: 'POST',
// 		query: {...query}
// 	})
// }


//GET 请求案例可以传递参数也可以不传递


export const getArticles  = (query) => {
 let str = query
 return apiResquest({
  url: '/c/article',
  method: 'GET',
  query: {...query}
 })
}