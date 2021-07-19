<template>
	<view class="">
		<pulldownfresh ref="mixPulldownMyCollection" class="panel-content" :top="90" @refresh="onPulldownMyCollection"
			>
			<view class="root">


				<view v-for="(item,index) in paintList" :key="index">
                 <!-- <navigator :url="'../../pages/relief/articledetail?key='+item.article.article_id+'&name='+item.nickname+'&headphoto='+item.avatar+'&time='+currenttime[index] "> -->
					<view class="view1" >
						 <!-- <navigator :url="'../../pages/relief/articledetail?key='+item.article.article_id+'&name='+item.nickname+'&headphoto='+item.avatar+'&time='+currenttime[index] "> -->
						<view class="view2">
							<image class="image1" :src="item.avatar"></image>
							<text style="font-size: 30rpx;">{{item.nickname}}</text>
							<image src="../../static/relief/menu.png" style="width: 50rpx;height: 50rpx; margin-left: 270rpx;" @click="open(index,item)" :data-id="'center'"></image>
						</view>
						<view class="view3">
							<image :src="item.article.pic_url" class="image2" mode="aspectFit" @tap=' _previewImage(item.article.pic_url)'>
							</image>
						</view>
						<!-- </navigator> -->
						<view class="view4">
							<view class="text1" style="font-size: 28rpx;">{{currenttime[index]}}</view>
							<view class="view43">
								<view class="view41" @tap="poststar(item)" :id="index" >
									<image :src="isclick?star[1]:star[0]"></image>
									<view style="margin-left: 10rpx;"><text style="font-size: 28rpx;">{{item.article.star}}</text></view>
								</view>
								<view class="view42" @tap="replynum(item,index)" :id="index">
									<image src="../../static/relief/comment.png"></image>
									<view style="margin-left: 10rpx;"><text style="font-size: 28rpx;">{{item.article.comment}}</text></view>
								</view>
							</view>
						</view>
                        
						
					</view>
					<!-- <pop ref="pop" :direction="direction" :is_close="true" :is_mask="true" :width="100">
					
						<view v-for="t in 5" :key="t">
							我是：{{direction}}一个弹窗
						</view>
					
					</pop> -->
					<!-- </navigator> -->
				</view>
               <!-- <pop ref="pop" :direction="direction" :is_close="true" :is_mask="true" :width="100">
                
                	<view v-for="t in 5" :key="t">
                		我是：{{direction}}一个弹窗
                	</view>
                
                </pop> -->
			</view>
		</pulldownfresh>
	</view>
</template>

<script>
	
	import pop from "@/components/ming-pop/ming-pop.vue"
	import commentlist from "../../components/paintlist/comment.vue"
	import pulldownfresh from "../mix-pulldown-refresh/mix-pulldown-refresh.vue"
	export default {
		//name: "list",
		components: {
			commentlist,
			pulldownfresh,
			pop,
		},
		data() {
			return {
				pageid:'commentlist',
				direction: "up",
				pages: 2,
				morepaintlist: [],
				triggered: false,
				starnum:null,
				starreturn:null,
				articlenum: null,
				paintList: [],
				currenttime: [],
				isclick:false,
				clicknum:0,
				star: ["../../static/relief/star.png", "../../static/relief/star2.png"]
			};
		},
		created() {
			this.getpaintlist();
			
		},
        onReachBottom() {
        	console.log('我触底了')
        },
		methods: {
			
			open(index,item) {
				var that=this;
				//this.$refs.pop.show();
				uni.showActionSheet({
					
				    itemList: ['删除帖子','收藏'],
				    success: function (res) {
				        //console.log('选中了第' + (res.tapIndex + 1) + '个按钮');
				        that.deleteArticle(index,item);
					},
				    fail: function (res) {
				        console.log(res.errMsg);
				    }
				});
			
			},
			deleteArticle(index,item){
				var _this=this;
				uni.showModal({
					    title: '友情提示',
					    content: '确定要删除这条帖子吗',
						
					    success: function (res) {
							
					        if (res.confirm) {
					            console.log('用户点击确定');
								console.log(index);
								console.log(item.article.article_id);
								console.log(_this.isclick)
								uni.request({
									url:'https://zk123.top/mye/c/article/delete',
									method: 'POST',
									header:{Authorization:uni.getStorageSync('token'),
									        'content-type':'application/x-www-form-urlencoded'
									},
									data:{
										articleId:item.article.article_id,
										
									},
									success:(res)=>{
										
										console.log(res);
										console.log(_this.paintList)
										_this.paintList.splice(index,1);
										uni.showToast({
											icon: 'loading',
											title:'删除成功'
										})
									},
									fail: (err) => {
										console.log(err)
									return uni.showToast({
										icon: 'loading',
										title:'请求失败'
									})
									}
									
								})
								
								
								
					        } else if (res.cancel) {
					            console.log('用户点击取消');
					        }
					    }
					});
				
			},
			_previewImage(image) {
							var imgArr = [];
							imgArr.push(image);
							//预览图片
							uni.previewImage({
								urls: imgArr,
								current: imgArr[0]
							});
						},
			

			getmore() {
				this.getmorepaintlist();

			},
			onPulldownMyCollection() {
				const _this = this;
				_this.getpaintlist();
                
				_this.$refs.mixPulldownMyCollection && _this.$refs.mixPulldownMyCollection.endPulldownRefresh();

			},
			
			poststar(item){
				
				console.log(item.article.article_id);
				console.log(uni.getStorageSync('token'))
				var a=uni.getStorageSync('token').split(" ");
				this.isclick=!this.isclick;
				console.log(this.isclick)
				const _this=this
				uni.request({
					url:'https://zk123.top/mye/c/star',
					method: 'POST',
					header:{Authorization:uni.getStorageSync('token'),
					        'content-type':'application/x-www-form-urlencoded'
					},
					data:{
						articleId:item.article.article_id,
						floor:'0'
					},
					success:(res)=>{
						console.log("123s")
						item.article.star=item.article.star+1;
						console.log(res);
						uni.showToast({
							icon: 'loading',
							title:'请求成功'
						})
					},
					fail: (err) => {
						console.log(err)
					return uni.showToast({
						icon: 'loading',
						title:'请求失败'
					})
					}
					
				})
				
			},
			replynum(item,index) {
				var that=this;
				
                 uni.navigateTo({
                      //url: '../../pages/relief/articledetail'
					  url:'../../pages/relief/articledetail?key='+item.article.article_id+'&name='+item.nickname+'&headphoto='+item.avatar+'&time='+that.currenttime[index] 
                 });
				
			},
			isinput: function(res) {

				if (res.detail.value != "") {

					this.listitem[res.currentTarget.id].disabled = false;
				} else {
					this.listitem[res.currentTarget.id].disabled = true;

				}

			},

			sendcomment(index) {
				this.listitem[index].iscomment = false;

			},
            
			getpaintlist() {
				uni.showLoading({
					title:"加载中"
				})
				var that = this;
				var a;
				var b;
				uni.request({
					url: 'https://zk123.top/mye/c/article',
					data: {
						userId: 1,
						tag: 'painting',
						limit: 10,
						offset: 1,
					},
					success: (res) => {
						console.log(res)
						that.paintList = res.data.data.articleList;
					    uni.hideLoading();
						that.articlenum = that.paintList.length;
						console.log(that.paintList.articleId)
						console.log(that.paintList);
						for (let i = 0; i < that.paintList.length; i++) {
							//console.log(that.paintList[i].article.article_id);
							a = that.paintList[i].article.article_id.split("_");
							b = a[1].split('-');
							//console.log(b[i])
							that.currenttime[i] = b[0] + "-" + b[1] + "-" + b[2] + ' ' + b[3] + ':' + b[4] +
								':' + b[5];
							console.log(that.currenttime);
							//console.log(b[0]+"-"+b[1]+"-"+b[2]+' '+b[3]+':'+b[4]+':'+b[5])
						}
					},
                      
				})
             
			},
			getmorepaintlist() {
				var that = this;
				var a;
				var b;

				uni.request({
					url: 'https://zk123.top/mye/c/article',
					data: {
						userId: 1,
						tag: 'painting',
						limit: 5,
						offset: that.pages,
					},
					success: (res) => {
						that.morepaintList = res.data.data.articleList;
						that.paintList = that.paintList.concat(that.morepaintList);
						that.articlenum = that.paintList.length;
						that.pages++;
						console.log(that.pages);
						that.onAbort();
						console.log(that.paintList);
						for (let i = 0; i < that.paintList.length; i++) {
							//console.log(that.paintList[i].article.article_id);
							a = that.paintList[i].article.article_id.split("_");
							b = a[1].split('-');
							//console.log(b[i])
							that.currenttime[i] = b[0] + "-" + b[1] + "-" + b[2] + ' ' + b[3] + ':' + b[4] +
								':' + b[5];
							//console.log(that.currenttime);
							//console.log(b[0]+"-"+b[1]+"-"+b[2]+' '+b[3]+':'+b[4]+':'+b[5])
						}
					},

				})

			}

		}
	}
</script>

<style>
	.text1 {
		color: #757571;
		width: 400rpx;
		margin-left: 10rpx;
	}

	.placehoderclass {

		font-size: 30rpx;
		margin-top: 8rpx;
		margin-left: 10rpx;

	}

	.commentbutton {
		background: green;
		color: #fff;
		display: flex;
		justify-content: center;
		align-items: center;
		/* margin-top: 5rpx ;
		margin-bottom: 5rpx; */
		margin-right: 5rpx;
		height: 70rpx;
		font-size: 30rpx;

	}

	.textinput {
		height: auto;
		background: #fff;
		margin-top: 10rpx;
		margin-bottom: 10rpx;
		margin-left: 10rpx;
		margin-right: 5rpx;
		width: 550rpx;
		height: auto;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.commentinput {
		background: #f9f9f9;
		margin: 5rpx;
		height: auto;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	.root {
		box-sizing: border-box;
		width: 750rpx;
		height: 100%;
	}

	.commentlist {
		width: 82%;
		margin-left: 15%;
		margin-right: 3%;
		border-radius: 30rpx;
	}

	.view1 {
		box-sizing: border-box;
		margin: 12rpx 15rpx;
		height: auto;
		background: #ffffff;
		display: flex;
		flex-direction: column;
		border-radius: 15rpx;
		box-shadow: 0px 0px 2px #666666;
	}

	.view2 {
		box-sizing: border-box;
		height: 114rpx;
		width: 100%;
		display: flex;
		align-items: center;
		border-radius: 30rpx;
	}

	.view2 text {
		font-size: 18px;
		color: #757571;
		line-height: 50rpx;
		margin: 32rpx 20rpx;
		display: flex;
		align-items: center;
	}

	.view3 {
		width: 100%;
		height: 480rpx;
		background: #fff;
		box-sizing: border-box;
	}

	.image2 {
		width: 100%;
		height: 480rpx;

	}

	.view4 {
		width: 100%;
		height: 90rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		box-sizing: border-box;
	}

	.view4 text {
		margin-top: 10rpx;
		margin-left: 20rpx;
		font-size: 20rpx;
		color: #757571;


	}

	.view41 {
		margin-top: 10rpx;
		margin-right: 30rpx;
		width: 120rpx;
		height: 60rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: center;

	}

	.view41 image {
		width: 45rpx;
		height: 45rpx;
		margin: 6rpx 0;
	}

	.view41 text {
		margin: 0rpx 0;
		font-size: 28rpx;
	}

	.view42 text {
		margin: 0rpx 0rpx;
		font-size: 28rpx;
	}

	.view43 {
		right: 30rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: center;

	}

	.view42 {
		margin-top: 10rpx;
		margin-right: 30rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		width: 120rpx;
		height: 60rpx;
	}

	.view42 image {
		width: 45rpx;
		height: 45rpx;
		margin: 6rpx 0;
	}

	.image1 {
		height: 60rpx;
		width: 60rpx;
		border-radius: 45rpx;
		margin: 12rpx;
	}
</style>
