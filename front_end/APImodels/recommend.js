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


// export const getUserInfo  = (query) => {
//  let str = query
//  return apiResquest({
//   url: '/people/info',
//   method: 'GET'
//  })
// }

export const updateUserInfo = (query) => {
	return apiResquest({
		url: '/people/update',
		method: 'POST',
		query: {...query}
	})
}

export const updatePwd = (query) => {
	return apiResquest({
		url: '/people/pwd',
		method: 'POST',
		query: {...query}
	})
}

export const setAvatar = (query) => {
	return apiResquest({
		url: '/people/avatar',
		method: 'POST',
		query: {...query}
	})
}

export const getDiaryByKeyword = (query) =>{
	return apiResquest({
		url: '/dairy/keyword',
		method: 'GET',
		query: {...query} // 参数
	})
}
