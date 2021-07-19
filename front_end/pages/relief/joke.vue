<template>
	<view class="root">
		<view class="content">
			<view v-for="(item,index) in list" class="item" @tap='gotodetail(index,item)'>
				{{item.title}}
			</view>
		</view>
	</view>
</template>

<script>
	export default {
	
		data() {
			return {
				list:[{title:"一个伞兵"},{title:'表白'},{title:'近视'},{title:'叔叔还是阿姨'},{title:'试穿'},{title:'找老婆'},{title:'单身'},{title:'便秘'}]
			}
		},
		onLoad: function(option) {

		},
		methods:{
			gotodetail(index,item){
				uni.navigateTo({
					url: '../../pages/relief/jokedetail?id=' + index+'&title='+item.title
				});
			}
		}
		}
</script>

<style>
	page{
		background: #fff;
	}
	.item{
		width: 80%;
		height: 120rpx;
		border: 5rpx solid #4c9acc;
		border-radius: 10rpx;
		margin: 30rpx 10%;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		color: #4c9acc;
		font-size: 50rpx;
	}
	.root{
		
	}
</style>
