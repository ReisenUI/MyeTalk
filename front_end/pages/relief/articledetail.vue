<template>
	<view class="root">
		<view class="navbar">
			<image src="../../static/fanhui.png" style="width: 80rpx;height: 80rpx;" @click="goback"></image>
			<image class='headphoto' :src="headphoto"></image>
			<view style="font-size: 28rpx;color: #757571;">{{name}}</view>
		</view>
		<view class="content">
			<view class="picture">
				<image :src='picture' mode="aspectFit" style="height: 500rpx;"></image>
			</view>
			<view class="time" style="font-size: 28rpx;color: #757571;">
				{{time}}
			</view>
			<view class="starandcomment">
				<view class="star">
					<image src="../../static/relief/star.png" style="width: 40rpx;height: 40rpx;"></image>
					<view style="font-size: 30rpx;color: #757571; margin-left: 10rpx;">{{starnum}}</view>
				</view>
				<view class="comment">
					<image src="../../static/relief/comment.png" style="width: 40rpx;height: 40rpx;" @click="toggleMask('show')" ></image>
					<view style="font-size: 30rpx;color: #757571; margin-left: 10rpx;">{{commentnum}}</view>
				</view>
			</view>
			<image src="../../static/relief/line.png" style="height: 30rpx; width: 650rpx; margin-left: 50rpx;margin-bottom: 50rpx;"></image>
			<view style="width: 80%;height: auto;margin-left: 20%;">
				<commentlist  class="commentlist" :getcoment="articleid" ref="getcomment" >
				</commentlist>

			</view>

		</view>
        <ygc-comment ref="ygcComment"
            :placeholder="'发布评论'" 
        	@pubComment="pubComment"
			:getarticleid="articleid"
			@getcomment='getcomment'
			></ygc-comment>
	</view>
</template>

<script>
	import ygcComment from '@/components/ygc-comment/ygc-comment.vue';
	import commentlist from "../../components/paintlist/comment.vue"
	export default {
		components: {
			commentlist,
			ygcComment
		},
		data() {
			return {
				title: 'Hello',
				name: null,
				articleid: null,
				headphoto: null,
				picture: null,
				starnum: null,
				commentnum: null,
				time: null,
				pageid:null,
				iscomment:0

			}
		},
		created() {
			this.getdetail();

		},
		onLoad(options) {
			console.log(options.key)
			console.log(options.name)
			console.log(options.headphoto)
			console.log(options.time)
			this.articleid = options.key
			this.name = options.name
			this.headphoto = options.headphoto
			this.time = options.time
			this.getdetail();
			
		},
		methods: {
			toggleMask(type) {
				this.$refs.ygcComment.toggleMask(type);
			},
			pubComment(commentContent1) {
				this.$refs.ygcComment.toggleMask();
				console.log(commentContent1);
				
				this.commentContent = commentContent1;
				this.$refs.ygcComment.content = '';
				
			},
			goback() {
				uni.navigateTo({
					url: "/pages/relief/paint"
				})
			},
			getcomment(){
				this.$refs.getcomment.getcommentList();
				this.commentnum=this.commentnum+1;
				console.log("获取成功")
			},
			getdetail() {
				uni.showLoading({
					title:"加载中"
				})
				var that = this
				uni.request({
					url: 'https://zk123.top/mye/c/article/detail',
					data: {
						articleId: this.articleid
					},
					success: (res) => {
						console.log(res);
						uni.hideLoading();
						that.picture = res.data.data.article.pic_url;
						console.log(that.picture);
						that.starnum = res.data.data.article.star;
						that.commentnum = res.data.data.article.comment
						
					},

				})
			}
		}
	}
</script>

<style>
	.commentlist {
	
		height: auto;
		border-radius: 30rpx;
		
	}
	.comment {
		display: flex;
		flex-direction: row;
		
	}

	.star {
		display: flex;
		flex-direction: row;
		margin-left: 550rpx;
		margin-right: 40rpx;
	}

	.starandcomment {
		width: 100%;
		height: 50rpx;
		display: flex;
		flex-direction: row;
	}

	.time {
		margin-left: 20rpx;
	}

	.picture {
		width: 100%;
		height: 500rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.headphoto {
		width: 70rpx;
		height: 70rpx;
		border-radius: 35rpx;
		margin-left: 50rpx;
		margin-right: 20rpx;
	}

	.navbar {
       display: flex;
       flex-direction: row;
       align-items: center;
       width: 100%;
       height: 120rpx;
       background-color: #FFFFFF;
       position: fixed;
       top: --window-top;
       /* css变量 */
       left: 0;
       top: 0;
       z-index: 99;
       text-align: center;
	}

	.root {
		display: flex;
		flex-direction: column;
		width: 100%;
		height: 100%;
        

	}

	.content {
		display: flex;
		flex-direction: column;
		overflow-y: scroll;
		width: 100%;
		height: auto;
		background: #fff;
		margin-top: 120rpx;
	}

	page {
        background: #fff;
		width: 100%;
		height: 100%;
	}
</style>
