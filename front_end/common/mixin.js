export default{
	methods: {
		setTitle(title){
			return uni.setNavigationBarTitle({
				title:title
			});
		},
	}
}
