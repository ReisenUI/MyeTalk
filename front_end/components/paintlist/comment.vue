<template>

	<view class="root">
		<view class="chatMessage" v-for="(item,index) in commentlist" :key="index">
			<view class="chatPerson">
				<view class="chatPerson-photo">
					<image :src="item.mainCommentBar.avatar" mode="aspectFit"></image>
				</view>
				<view class="chatPerson-info">
					<view class="name">{{item.mainCommentBar.nickname}}</view>
					<view class="time">{{currenttime[index]}}</view>
				</view>
			</view>

			<view class="chatContent">
				{{item.mainCommentBar.content}}
			</view>
		</view>
	</view>


</template>

<script>
	export default {
		props: {
			getcoment: String,
			
		},
		data() {

			return {
				pages: 1,
				commentlist: [],
                currenttime:[],
			}
		},
		mounted() {
			//this.commentlist2=this.paintList,
			console.log(this.getcoment)
			this.getcommentList();
		},

        
		methods: {
			getcommentList() {
				var that = this;
				var a;
				var b;
				uni.request({
					url: 'https://zk123.top/mye/c/cmt',
					data: {
						articleId: that.getcoment,
						limit: 10,
						offset: 1,
					},
					success: (res) => {
						console.log(res);
						that.commentlist = res.data.data.comments;
						console.log(that.commentlist);
                        for (let i = 0; i < that.commentlist.length; i++) {
                        	//console.log(that.paintList[i].article.article_id);
                        	b= that.commentlist[i].mainCommentBar.time.split("-");
                        	
                        	//console.log(b[i])
                        	that.currenttime[i] = b[0] + "-" + b[1] + "-" + b[2] + ' ' + b[3] + ':' + b[4] +
                        		':' + b[5];
                        	//console.log(that.currenttime);
                        	//console.log(b[0]+"-"+b[1]+"-"+b[2]+' '+b[3]+':'+b[4]+':'+b[5])
                        }

					},

				})

			},
		}
	}
</script>

<style lang="scss">
	.root {
      
		border-radius: 30rpx;
		background-color: #f9f9f9;
	}

	.chatMessage {
		
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		padding: 10rpx 40rpx;
		//border-bottom: 1px solid #cdcdcd;

		.chatPerson {
			display: flex;
			box-sizing: border-box;
			height: 100rpx;
			padding: 4rpx 0;
			align-items: center;

			.chatPerson-photo image {
				margin: 10rpx 0;
				margin-right: 10rpx;
				width: 60rpx;
				height: 60rpx;
				border-radius: 50%;

			}

			.chatPerson-info {
				margin: 10rpx;

				.name {
					font-size: 30rpx;
					color: #000;
				}

				.time {
					font-size: 24rpx;
					color: #999;
				}
			}
		}


		.chatContent {
			display: flex;
			float: left;
			font-size: 30rpx;
			line-height: 1.6;
			color: #555;
			margin: 10rpx 80rpx;
		}


	}

	// .chatMessage:nth-child(3){
	// 	border-bottom: none;
	// }
</style>
