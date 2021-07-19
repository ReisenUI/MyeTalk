<template>
	<view class="page">
		<view class="nav">
			<view class="navbar">
				<view @click="gotorelief">
					<image src="/static/relief/fanhui.png" class="icon1"></image>
				</view>
				<view style="margin-top: 9px; ">
					<text style="font-size: 34rpx;font-weight: bold;">灵魂画手</text>
				</view>
				<view @click="gotodraw">
					<image src="/static/relief/paint.png" class="icon2"></image>
				</view>
			</view>

			<view class="tab">
				<view id="0" style="margin-right: 20px; " :class="[currentindex==0?'selected':'normal']"
					@click="changecurrentindex">
					<text style="font-size: 32rpx;;">推荐</text>
				</view>
				<view id="1" style=" margin-left: 20px;font-size: 32rpx;" :class="[currentindex==1?'selected':'normal']"
					@click="changecurrentindex2">
					我的
				</view>
			</view>
		</view>
		<view class="list" id="listborder">
			<view v-if="this.currentindex==0" id="listtop">
				<pulldownfresh ref="mixPulldownMyCollection" class="panel-content" :top="90"
					@refresh="onPulldownMyCollection">
					<view class="root">
						<view v-for="(item,index) in paintList" :key="index">
							<view class="view1">
								<view class="view2">
									<view class="image1">
										<image :src="item.avatar"></image>
									</view>
									<view class="title-tab">
										<text>{{item.nickname}}</text>
									</view>
									<view class="more" @click="openMenu(index,item)" :data-id="'center'">
										<uni-icons type="more-filled" size="50"></uni-icons>
									</view>
								</view>
								<view class="view3">
									<image :src="item.article.pic_url" class="image2" mode="aspectFit"
										@tap=' _previewImage(item.article.pic_url)'>
									</image>
								</view>
								<view class="view4">
									<view class="text1" style="font-size: 28rpx;">{{currenttime[index]}}</view>
									<view class="view43">
										<view class="view41" @tap="poststar(item)" :id="index">
											<image :src="item.started?star[1]:star[0]"></image>
											<view style="margin-left: 10rpx;"><text
													style="font-size: 28rpx;">{{item.article.star}}</text></view>
										</view>
										<view class="view42" @tap="replynum(item,index)" :id="index">
											<image src="../../static/relief/comment.png"></image>
											<view style="margin-left: 10rpx;"><text
													style="font-size: 28rpx;">{{item.article.comment}}</text></view>
										</view>
									</view>
								</view>
							</view>
						</view>
						<uni-load-more iconType="snow" :status="status"></uni-load-more>
					</view>
				</pulldownfresh>
			</view>
			<view v-if="this.currentindex==1">
				<!-- <pulldownfresh ref="mixPulldownMyCollection" class="panel-content" :top="90" @refresh="onPulldownMyCollection"
					> -->
				<view class="root">
					<view v-for="(item,index) in mimelist" :key="index" v-if="item.article.tag=='painting'">
						<view class="view1">
							<view class="view2">
								<view class="image1">
									<image :src="item.avatar"></image>
								</view>
								<view class="title-tab">
									<text>{{item.nickname}}</text>
								</view>
								<view class="more" @click="openMenu(index,item)" :data-id="'center'">
									<uni-icons type="more-filled" size="50"></uni-icons>
								</view>
							</view>
							<view class="view3">
								<image :src="item.article.pic_url" class="image2" mode="aspectFit"
									@tap=' _previewImage(item.article.pic_url)'>
								</image>
							</view>
							<view class="view4">
								<view class="text1" style="font-size: 28rpx;">{{mimecurrenttime[index]}}</view>
								<view class="view43">
									<view class="view41" @tap="poststar(item)" :id="index">
										<image :src="item.started?star[1]:star[0]"></image>
										<view style="margin-left: 10rpx;"><text
												style="font-size: 28rpx;">{{item.article.star}}</text></view>
									</view>
									<view class="view42" @tap="replynum(item,index)" :id="index">
										<image src="../../static/relief/comment.png"></image>
										<view style="margin-left: 10rpx;"><text
												style="font-size: 28rpx;">{{item.article.comment}}</text></view>
									</view>
								</view>
							</view>
						</view>
					</view>
					<!-- <uni-load-more iconType="snow" :status="status"></uni-load-more> -->
				</view>
				<!-- </pulldownfresh> -->
			</view>
		</view>
	</view>
</template>

<script>
	import pop from "@/components/ming-pop/ming-pop.vue"
	import commentlist from "../../components/paintlist/comment.vue"
	import pulldownfresh from "../../components/mix-pulldown-refresh/mix-pulldown-refresh.vue"
	import listitem from '../../components/paintlist/list.vue'
	import listitem2 from '../../components/paintlist/list2.vue'
	var _self = this;
	export default {
		//name:"first-nav",
		components: {
			listitem,
			listitem2,
			commentlist,
			pulldownfresh,
			pop,
		},
		data() {
			return {
				baseUrl: 'https://zk123.top/mye',
				currentindex: 0,
				iscomment: false,
				pageid: 'commentlist',
				direction: "up",
				pages: 2,
				morepaintlist: [],
				triggered: false,
				starnum: null,
				starreturn: null,
				articlenum: null,
				paintList: [],
				currenttime: [],
				isclick: false,
				clicknum: 0,
				star: ["../../static/relief/star.png", "../../static/relief/star2.png"],
				status: 'more',
				userId: 1,
				tag: 'painting',
				pageSize: 4,
				pageNumber: 1,
				userInfo: {},
				ismine: null,
				mimelist: [],
				mimecurrenttime: [],
				
			};
		},
		onReachBottom() {
			console.log('chudil');
			this.getmorepaintlist();
		},
		onLoad() {
			this.pageNumber=1;
			//this.togglePosition();
			this.getUserInfo();
			this.getpaintlist();
			this.getmimelist();
		},
		onShow() {
			this.pageNumber=1;
			//this.togglePosition();
			//this.getpaintlist();
			//this.getmimelist();
		},
		methods: {
			togglePosition(){
			            uni.createSelectorQuery().select('.listtop').boundingClientRect(data=>{//目标位置的节点：类或者id
			                  uni.createSelectorQuery().select(".listborder").boundingClientRect(res=>{//最外层盒子的节点：类或者id
			                        uni.pageScrollTo({
			                              duration: 100,//过渡时间
			                              scrollTop:0  ,//返回顶部的top值
			                        })
			                  }).exec()
			            }).exec();
			
			      },
			changecurrentindex: function(e) {
				this.currentindex = e.target.id;
				this.pageNumber=1;
				this.togglePosition();
				this.getpaintlist();
			},
			changecurrentindex2: function(e) {
				this.currentindex = e.target.id;
				this.togglePosition();
				this.getmimelist();
			},
			gotorelief() {
				uni.switchTab({
					url: "/pages/relief/relief"
				})
			},
			gotodraw() {
				uni.navigateTo({
					url: "/pages/relief/draw"
				})
			},
			openMenu(index, item) {
				var _this = this;
				// 帖子的userId  数据的类型是string 需要先转换一下数据类型
				var article_userId = Number(item.article.article_id.slice(0, 5));
				var usrId = _this.userInfo.id;
				// 看是否为自己发的帖子
				if (_this.userInfo.id === article_userId) {
					uni.showActionSheet({
						itemList: ['删除帖子', '复制内容'],
						success: function(res) {
							var choseindex = res.tapIndex;
							if (choseindex === 0) {
								_this.deleteArticle(index, item);
							} else if (choseindex === 1) {
								console.log('复制内容');
								uni.showToast({
									title: '内容已复制'
								})
							}
						},
						fail: function(res) {
							console.log(res.errMsg);
						}
					});
				} else {
					uni.showActionSheet({
						itemList: ['复制内容'],
						success: function(res) {
							console.log(res.tapIndex);
							var index = res.tapIndex;
							if (index === 0) {
								console.log('复制内容');
								uni.showToast({
									title: '内容已复制'
								})
							}
						},
						fail: function(res) {
							console.log(res.errMsg);
						}
					});
				}
			},
			getUserInfo() {
				const _this = this
				uni.request({
					url: 'https://zk123.top/mye/people/info',
					data: {},
					header: {
						Authorization: uni.getStorageSync('token')
					},
					method: 'GET',
					success: (res) => {
						_this.userInfo = res.data.data;
						console.log('userInfo', _this.userInfo)
					}
				})
			},
			deleteArticle(index, item) {
				var _this = this;
				uni.showModal({
					title: '友情提示',
					content: '确定要删除这条帖子吗',

					success: function(res) {

						if (res.confirm) {
							console.log('用户点击确定');
							console.log(index);
							console.log(item.article.article_id);
							console.log(_this.isclick)
							uni.request({
								url: 'https://zk123.top/mye/c/article/delete',
								method: 'POST',
								header: {
									Authorization: uni.getStorageSync('token'),
									'content-type': 'application/x-www-form-urlencoded'
								},
								data: {
									articleId: item.article.article_id,

								},
								success: (res) => {

									console.log(res);
									console.log(_this.paintList)
									if (_this.currentindex == 1) {
										_this.mimelist.splice(index, 1)
										_this.mimecurrenttime.splice(index, 1)
									} else {
										_this.paintList.splice(index, 1)
										_this.currenttime.splice(index, 1)
									}
									uni.showToast({
										icon: 'success',
										title: '删除成功'
									})
								},
								fail: (err) => {
									console.log(err)
									return uni.showToast({
										icon: 'loading',
										title: '请求失败'
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
			onPulldownMyCollection() {
				const _this = this;
				_this.pageNumber = 1;
				_this.status = 'more';
				_this.getpaintlist();
				_this.$refs.mixPulldownMyCollection && _this.$refs.mixPulldownMyCollection.endPulldownRefresh();
			},
			judgeStar(id) {
				uni.request({
					url: 'https://zk123.top/mye/c/judgeStar',
					method: 'GET',
					header: {
						Authorization: uni.getStorageSync('token'),
						'content-type': 'application/x-www-form-urlencoded'
					},
					data: {
						articleId: id,
						floor: '0'
					},
					success: (res) => {
						console.log(res);
						uni.showToast({
							icon: 'loading',
							title: '请求成功'
						})
					},
					fail: (err) => {
						console.log(err)
						return uni.showToast({
							icon: 'loading',
							title: '请求失败'
						})
					}

				})
			},
			poststar(item) {
				console.log(item.article.article_id);
				const _this = this;
				var isstar = item.started;
				if (isstar == false) {
					item.started = true;
					console.log('now:' + item.started);
					item.article.star = item.article.star + 1;
				} else {

					item.started = false;
					console.log('now:' + item.started);
					item.article.star = item.article.star - 1;
				}
				uni.request({
					url: 'https://zk123.top/mye/c/star',
					method: 'POST',
					header: {
						Authorization: uni.getStorageSync('token'),
						'content-type': 'application/x-www-form-urlencoded'
					},
					data: {
						id: item.article.article_id,
						flag: '0'
					},
					success: (res) => {
						console.log(res);
						uni.showToast({
							icon: 'success',
							title: '请求成功'
						})

					},
					fail: (err) => {
						console.log(err)
						return uni.showToast({
							icon: 'loading',
							title: '请求失败'
						})
					}

				})

			},
			replynum(item, index) {
				var that = this;
				console.log('tiaozhaun')
				uni.navigateTo({
					//url: '../../pages/relief/articledetail'

					url: '../../pages/community-passage/passage-lingxian/passage-lingxian?articleId=' + item.article.article_id
				});

			},
			sendcomment(index) {
				this.listitem[index].iscomment = false;
			},
			getmimelist() {
				uni.showLoading({
					title: "加载中"
				})
				var that = this;
				var a;
				var b;

				uni.request({
					url: 'https://zk123.top/mye/c/homepage/article',
					header: {
						Authorization: uni.getStorageSync('token'),
						'content-type': 'application/x-www-form-urlencoded'
					},
					data: {
						userId: that.userInfo.id,
						limit: 100,
						offset: 1,
					},
					success: (res) => {
						console.log(res)
						that.mimelist = res.data.data.articleList;
						uni.hideLoading();
						//that.articlenum = that.paintList.length;
						//console.log(that.paintList.articleId)
						//console.log(that.paintList);
						for (let i = 0; i < that.mimelist.length; i++) {
							//console.log(that.paintList[i].article.article_id);
							a = that.mimelist[i].article.article_id.split("_");
							b = a[1].split('-');
							//console.log(b[i])
							that.mimecurrenttime[i] = b[0] + "-" + b[1] + "-" + b[2] + ' ' + b[3] + ':' + b[
								4] +
								':' + b[5];
							//console.log(that.currenttime);
							//console.log(b[0]+"-"+b[1]+"-"+b[2]+' '+b[3]+':'+b[4]+':'+b[5])
							uni.request({
								url: 'https://zk123.top/mye/c/judgeStar',
								header: {
									Authorization: uni.getStorageSync('token'),
									'content-type': 'application/x-www-form-urlencoded'
								},
								data: {
									id: that.mimelist[i].article.article_id,
									//floor:0
								},
								success: (e) => {
									console.log('getstar');
									console.log(i);
									console.log(e.data.data);
									that.mimelist[i].started = e.data.data;
									//console.log(that.mimelist[i].started)
								},

							})

						}
					},

				})

			},
			getpaintlist() {
				uni.showLoading({
					title: "加载中"
				})
				var that = this;
				var a;
				var b;
				uni.request({
					url: 'https://zk123.top/mye/c/article',
					data: {
						userId: 1,
						tag: 'painting',
						limit: that.pageSize,
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
							uni.request({
								url: 'https://zk123.top/mye/c/judgeStar',
								header: {
									Authorization: uni.getStorageSync('token'),
									'content-type': 'application/x-www-form-urlencoded'
								},
								data: {
									id: that.paintList[i].article.article_id,
									//floor:0
								},
								success: (e) => {
									console.log('getstar');
									console.log(i);
									console.log(e.data.data);
									that.paintList[i].started = e.data.data;
									console.log(that.paintList[i].started)
								},

							})

						}
					},

				})

			},
			getmorepaintlist() {
				var _this = this;
				if (_this.status === 'noMore') {
					return
				}
				_this.status = 'loading';
				_this.pageNumber++;
				uni.request({
					url: this.baseUrl + '/c/article', //接口地址
					method: 'GET',
					data: {
						userId: this.userId,
						tag: this.tag,
						limit: this.pageSize,
						offset: this.pageNumber
					},
					success: (res) => {
						console.log('articlelist')
						console.log(res.data);
						_this.uploadList = res.data.data.articleList;
						if (Math.ceil(res.data.data.totalSize / this.pageSize) < this.pageNumber) {
							_this.status = 'noMore';
						} else {
							let obj = res.data.data.articleList;
							setTimeout(function() {
								let tempList = res.data.data.articleList;

								let temptime = [];
								var a, b;
								for (let i = 0; i < _this.uploadList.length; i++) {
									a = _this.uploadList[i].article.article_id.split("_");
									b = a[1].split('-');
									//console.log(b[i])
									temptime[i] = b[0] + "-" + b[1] + "-" + b[2] + ' ' + b[3] + ':' +
										b[4] +
										':' + b[5];
									console.log(temptime);
									_this.currenttime.push(temptime[i]);
									uni.request({
										url: 'https://zk123.top/mye/c/judgeStar',
										header: {
											Authorization: uni.getStorageSync('token'),
											'content-type': 'application/x-www-form-urlencoded'
										},
										data: {
											id: _this.uploadList[i].article.article_id,
											//floor:0
										},
										success: (e) => {
											console.log('getagainstar');
											console.log(i);
											console.log(e.data.data);
											_this.uploadList[i].started = e.data.data;
											console.log(_this.uploadList[i].started)
										},

									})


								}
								for (var i = 0; i < _this.uploadList.length; i++) {
									_this.paintList.push(_this.uploadList[i]);
								}
								console.log(_this.paintList);
							}, 1000);
						}
					}
				});

			}

		}
	}
</script>

<style lang="scss">
	.nav {

		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 200upx;
		background-color: #FFFFFF;
		position: fixed;
		top: --window-top;
		/* css变量 */
		left: 0;
		top: 0;
		z-index: 99;
		text-align: center;
	}

	.page {
		display: flex;
		flex-direction: column;
		width: 100%;
		height: 100%;
		position: fiex;
		/* position: relative; */
	}

	text {
		font-size: 25px;
		padding-top: 15px;
	}

	.list {
		display: flex;
		flex-direction: column;
		margin-top: 200rpx;
		/* overflow-y: scroll; */
		width: 100%;

		height: 100%;
		//background: #e3e3d3;
		background: #f8f8f8;
	}

	.icon1 {
		height: 70rpx;
		width: 70rpx;
		margin-top: 10rpx;

	}

	.icon2 {
		height: 90rpx;
		width: 90rpx;


	}

	.navbar {
		margin-top: 0px;
		width: 100%;
		height: 50px;
		display: flex;
		justify-content: space-between;
		background: #ffffff;
		/* position: fixed;
		z-index: 100;
		top: 0; */
	}

	.tab {

		display: flex;
		align-items: center;
		justify-content: space-around;
		background: #ffffFF;
		height: 90rpx;
		width: 100%;
		padding: 0 100rpx;
		box-sizing: border-box;
	}



	.selected {
		/* width: 100rpx;
		margin-left: 200rpx;
		border-bottom: 2px solid #4c9acc;
		/* text-align: center; */
		color: #4C9ACC;
		/* display: flex; */
		/* align-items: center; */
		border-bottom: 2px solid #4c9acc;
		padding-bottom: 6rpx;
	}

	.normal {
		color: #777;
		/* width: 100rpx; */
		/* margin-left: 200rpx; */
		/* border-bottom: none; */
		/* text-align: center; */
		/* display: flex; */
		/* align-items: center; */

	}

	/* .view2 .text{
			
			display: inline;
			color: #000000;
			margin: 10px 50px;
			font-size: 20px;
		} */

	page {
		background: #f8f8f8;
		//background-color: #f8f8f8;
		/* display: -webkit-box; */
		width: 100%;
		height: 100%;
	}

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
		flex-direction: row;
		justify-content: space-between;
		position: relative;
		border-radius: 30rpx;
		padding: 0 20rpx;

		.image1 {
			width: 60rpx;
			height: 60rpx;

			image {
				width: 100%;
				height: 100%;
				border-radius: 50%;
			}
		}

		.title-tab {
			display: flex;
			align-items: center;
			justify-content: center;
			position: absolute;
			left: 100rpx;
			top: 0rpx;

			text {
				font-size: 30rpx;
			}
		}
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
</style>
