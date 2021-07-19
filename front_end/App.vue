<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	page {
		box-sizing: border-box;
		background-color: #f9f9f9;
		/* font-family: "华文新魏"; */
	}
	
	button::after {
		border: none; // 去掉按钮的自带边框线
	}

	/*每个页面公共css */
</style>
