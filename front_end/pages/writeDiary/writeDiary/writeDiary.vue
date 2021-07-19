<template>
	<view>
		<navigation-button :startDay="diary.date" @selectDate="selectDate"></navigation-button>
		<view id="test" class="addImage-box">
			<view class="addImage-item" v-for="(item,index) in imageList" :key="index">
				<view class="close-icon" @click="delImg(index)">
					<uni-icons type="closeempty" size="50" color="#fff"></uni-icons>
				</view>
				<view class="image-box">
					<image :src="item.url" mode="aspectFill"></image>
				</view>
			</view>

			<view v-if="imageList.length < 1 && !isReadDiary" class="addImage-item" @click="chooseImage">
				<view class="image-box">
					<view class="plusPhoto">
						<uni-icons type="plusempty" size="100" color="#fff"></uni-icons>
					</view>
				</view>
			</view>
		</view>
		<view class="content">
			<textarea class="content_title" placeholder="起个标题叭..." :value="title" @input="titleInput" />
			<movable-area :id="writeArea.writeAreaId"
				:style="{'border': 'red dashed ' + writeArea.writeAreaBorder + 'px', 'width': '100%', 'height': '1500rpx'}">
				<movable-view
					:style="{'width': item.width+'rpx', 'height': item.height+'rpx', 'border': item.isShowBorder ? '#000000 dashed 5rpx' : ''}"
					:id="item.itemId" @touchend="touchEnd" @touchstart="touchStart" direction="all" :x="item.posX"
					:y="item.posY" v-for="item in dragBoxList.list" :key="item.itemId">

					<textarea :id="item.itemId" class="textArea" @longpress="longPress" @input=getValue
						:value="item.valueSync" :style="{ 'width': item.width + 'rpx', 'height': item.height+ 'rpx'}"
						maxlength="-1" @click="sizeController(item)" />
				</movable-view>
			</movable-area>
		</view>
		<view class="tabBar">
			<button class="text" @tap="addDragBox()">添加一个文本框</button>
			<image class="bubble" mode="aspectFit" src="../../../static/writeDiary/bubble.png" @tap="bubble()"></image>
			<image class="draft" mode="aspectFit" src="../../../static/writeDiary/draft.png" @tap="saveDraft()"></image>
		</view>
	</view>
</template>

<script>
	import {
		Diary
	} from '../../../common/Diary.js'
	import {
		DiaryList
	} from '../../../common/DiaryList.js'
	import {
		getWritingDiary,
		storageWritingDiary,
		getPostHead,
		getGetHead,
		getUrl,
		getDiaryList,
		compareDate,
		refreshDiary,
		storageDraftItem,
		deleteDraftItem
	} from '../../../common/util/util.js'
	class DragBoxItem {
		constructor(id) {
			this.itemId = "dragBox" + id; // dragBoxList.list的长度-1
			this.posX = 197;
			this.posY = 158;
			this.width = 200; // rpx
			this.height = 200; // rpx
			this.valueSync = '';
			this.isShowBorder = 'false';
		}
	}

	class WriteArea {
		constructor(id) {
			this.writeAreaId = "writeArea" + id;
			this.writeAreaBorder = 0; // px
		}
	}
	export default {
		// 双击变大，三击变小，长按删除
		data() {
			return {
				isReadDiary: false,
				title: '',
				imageList: [],
				dragBoxList: {
					maxId: 0,
					list: []
				},
				diary: null,
				scroll: 0,
				writeArea: new WriteArea(0),
				clickTime: 0
				// defaultCover: ''
			}
		},
		methods: {
			titleInput(event) {
				this.title = event.target.value;
			},
			sizeController(item) {
				this.clickTime++;
				let that = this;
				const time = setTimeout(function() { // 初始化一个延时
					if (that.clickTime == 1) {
						console.log('单击');
						/* 单击后要处理的业务 */

					} else if (that.clickTime == 2) {
						console.log('双击');
						that.addSize(item);
						/* 双击后要处理的业务 */
					} else if (that.clickTime == 3) {
						console.log("三击");
						that.subSize(item);
					}
					// clearTimeout(time);
					that.clickTime = 0
				}, 300)
			},
			addSize(item) {
				item.width += 10;
				item.height += 10;
			},
			subSize(item) {
				item.width -= 10;
				item.height -= 10;
			},
			longPress(e) {
				console.log("longPress:", e);
				this.removeDragBox(e.target.id);
			},
			addDragBox() {
				// TODO: 如果不按序删除会出现id重复
				for (let i in this.dragBoxList.list) {
					let item = this.dragBoxList.list[i];
					if (item.valueSync == '') {
						this.removeDragBox(item.itemId);
					} else if (item.isShowBorder) {
						item.isShowBorder = false;
					}
				}

				let id = this.dragBoxList.maxId++;
				let item = new DragBoxItem(id);
				// this.dragBoxList.list.splice(id, 0, item);
				let length = this.dragBoxList.list.length;
				this.$set(this.dragBoxList.list, length, item);
			},
			removeDragBox(id) {
				// id = '#' + id;
				for (let i in this.dragBoxList.list) {
					let item = this.dragBoxList.list[i];
					if (item.itemId == id) {
						this.dragBoxList.list.splice(i, 1);
						break;
					}
				}
			},
			touchStart(e) {
				console.log('s:', e);
				var id = e.target.id; // 没有#
				for (let i in this.dragBoxList.list) {
					let item = this.dragBoxList.list[i];
					if (this.dragBoxList.list[i].itemId == id) {
						item.isShowBorder = true;
					} else {
						if (item.valueSync == '') {
							this.removeDragBox(item.itemId);
						} else {
							item.isShowBorder = false;
						}
					}
				}
			},
			touchEnd(e) {
				// 保存的时候遍历一遍好了
				var id = e.target.id; // 没有#
				for (let i in this.dragBoxList.list) {
					if (this.dragBoxList.list[i].itemId == id) {
						let item = this.dragBoxList.list[i];
						this.getMoveItemPosX(id, item);
						this.getMoveItemPosY(id, item);
						console.log(JSON.stringify(item)); // 虽然显示的不对，但是实际上是对的
						break;
					}
				}
			},
			onPageScroll(e) {
				this.scroll = e.scrollTop;
			},
			getMoveItemPosX(id, item) {
				id = '#' + id;
				var posX = 0;
				var posXF = 0;
				var query = uni.createSelectorQuery().in(this);
				query.select(id).boundingClientRect();
				query.select('#' + this.writeArea.writeAreaId).boundingClientRect();
				query.exec(data => {
					posX = data[0].left;
					posXF = data[1].left;
					item.posX = posX - this.writeArea.writeAreaBorder - posXF;
				})
			},
			getMoveItemPosY(id, item) { // TODO: 现在获得的是全局Y，需要相对父节点的坐标，去掉父节点边框的宽度
				id = '#' + id;
				let posY = 0;
				let posYF = 0;
				let that = this;
				var tt = uni.createSelectorQuery().in(this);
				tt.select(id).boundingClientRect();
				tt.select('#' + this.writeArea.writeAreaId).boundingClientRect();
				tt.exec(data => {
					// 是异步的cnm
					posY = data[0].top + that.scroll;
					posYF = data[1].top + that.scroll;
					item.posY = posY - posYF - this.writeArea.writeAreaBorder;
				});
			},
			getValue(event) {
				var id = event.target.id; // 没有#
				for (let i in this.dragBoxList.list) {
					if (this.dragBoxList.list[i].itemId == id) {
						let item = this.dragBoxList.list[i];
						item.valueSync = event.target.value;
						break;
					}
				}
			},
			onNavigationBarButtonTap(e) {
				this.saveDiary(true);
			},
			saveDiary(isFinish) {
				var draftID = -1;
				if (this.diary.id != 0 && !this.diary.isCompete) {
					draftID = this.diary.id;
				}
				var diaryList = getDiaryList();
				if (this.title == '')
					this.title = '这是一篇没有标题的日记';
				this.diary.setTitle(this.title);
				this.diary.setContent(this.dragBoxList);
				let today = new Date();
				let date = this.diary.date;
				if (this.diary.id == 0) {
					this.diary.id = diaryList.maxId++;
					diaryList.list.splice(0, 0, this.diary);
				} else {
					refreshDiary(this.diary)
				}
				if (!isFinish) {
					// 存草稿
					return;
				}
				if (today.getFullYear() == date.year && today.getMonth() + 1 == date.month && today.getDate() == date
					.day) {
					let isTodayWrited = {
						date: today,
						isTodayWrited: true
					}
					uni.setStorageSync('isTodayWrited', isTodayWrited);
				}
				this.diary.isCompete = true;

				// 上传到服务器和保存到本地缓存
				uni.setStorageSync('diaryList', diaryList);

				// storageWritingDiary(this.diary); // TODO: 可删
				if (this.diary.cover == null) {
					uni.showToast({
						position: 'bottom',
						title: '请选择封面',
						icon: 'none'
					})
					return;
					// this.diary.cover = this.defaultCover;
				}


				var _this = this;
				uni.showLoading({
					title: "上传中...",
					mask: true
				})
				// if (typeof(this.diary.cover) == 'object') {
				if (this.diary.cover.indexOf('https') == -1) {
					uni.uploadFile({
						url: getUrl('/c/upload'),
						name: 'file',
						filePath: _this.diary.cover,
						header: getGetHead(),
						success(res) {
							console.log(res);
							res.data = JSON.parse(res.data);
							_this.diary.cover = res.data.data;
							uni.request({
								url: getUrl('/diary/create'),
								method: 'POST',
								header: getPostHead(),
								data: {
									diaryId: JSON.stringify(_this.diary.diaryId),
									background: _this.diary.cover,
									date: _this.diary.date.year + '-' + _this.diary.date.month + '-' +
										_this
										.diary.date.day,
									title: _this.diary.title,
									emotions: JSON.stringify(_this.diary.mood),
									activities: JSON.stringify(_this.diary.activities),
									diaryContent: JSON.stringify(_this.diary.content),
									weather: JSON.stringify(_this.diary.weather)
								},
								success(res2) {
									// uploadfile获得的data就是string
									// res.data = JSON.parse(res.data);
									console.log(res2);
									_this.diary.diaryId = res2.data.data.diaryId;
									_this.diary.cover = res2.data.data.imageAddr;
									// 删除草稿本中的项目
									if (draftID != -1) {
										let draft = new Diary();
										draft.id = draftID;
										deleteDraftItem(draft);
									}

									refreshDiary(_this.diary);
									console.log(_this.diary);
									uni.hideLoading();
									uni.reLaunch({
										url: '../../index/index'
									});
								},
								fail: () => {},
								complete: () => {}
							});
						}
					});
				} else {
					uni.request({
						url: getUrl('/diary/create'),
						method: 'POST',
						header: getPostHead(),
						data: {
							diaryId: JSON.stringify(_this.diary.diaryId),
							background: _this.diary.cover,
							date: _this.diary.date.year + '-' + _this.diary.date.month + '-' + _this
								.diary.date.day,
							title: _this.diary.title,
							emotions: JSON.stringify(_this.diary.mood),
							activities: JSON.stringify(_this.diary.activities),
							diaryContent: JSON.stringify(_this.diary.content),
							weather: JSON.stringify(_this.diary.weather)
						},
						success(res) {
							// uploadfile获得的data就是string
							// res.data = JSON.parse(res.data);
							console.log(res);
							_this.diary.diaryId = res.data.data.diaryId;
							_this.diary.cover = res.data.data.imageAddr;
							// 删除草稿本中的项目
							if (draftID != -1) {
								let draft = new Diary();
								draft.id = draftID;
								deleteDraftItem(draft);
							}

							refreshDiary(_this.diary);
							console.log(_this.diary);
							uni.hideLoading();
							uni.reLaunch({
								url: '../../index/index'
							});
						},
						fail: () => {},
						complete: () => {}
					});
				}


				/* uni.uploadFile({
					url: getUrl('/diary/create'),
					name: 'background',
					filePath: _this.diary.cover,
					header: getGetHead(),
					formData: {
						diaryId: JSON.stringify(_this.diary.diaryId),
						// date: JSON.stringify(_this.diary.date),
						date: _this.diary.date.year + '-' + _this.diary.date.month + '-' + _this.diary.date.day,
						title: _this.diary.title,
						emotions: JSON.stringify(_this.diary.mood),
						activities: JSON.stringify(_this.diary.activities),
						diaryContent: JSON.stringify(_this.diary.content),
						weather: JSON.stringify(_this.diary.weather)
					},
					success(res) {
						// uploadfile获得的data就是string
						console.log(res);
						res.data = JSON.parse(res.data);
						_this.diary.diaryId = res.data.data.diaryId;
						_this.diary.cover = res.data.data.imageAddr;
						// 删除草稿本中的项目
						if (draftID != -1) {
							let draft = new Diary();
							draft.id = draftID;
							deleteDraftItem(draft);
						}

						refreshDiary(_this.diary);
						console.log(_this.diary);
						uni.hideLoading();
						uni.reLaunch({
							url: '../../index/index'
						});
					}
				}) */
			},
			setTitle() {
				uni.setNavigationBarTitle({
					title: this.diary.date.year + '年' + this.diary.date.month + '月' + this.diary.date.day + '日∨'
				});
			},
			selectDate(date) {
				this.diary.date = date;
				this.setTitle();
				console.log(this.diary);
			},
			bubble(){
				uni.showToast({
					position: 'bottom',
					title: '开发中...',
					icon: 'none'
				})
			},
			saveDraft() {
				console.log("草稿");
				// return;
				this.saveDiary(false);
				this.diary.isCompete = false;
				storageDraftItem(this.diary);
				uni.showToast({
					title: "存入草稿箱",
					position: 'bottom',
					icon: 'none'
				});
				uni.reLaunch({
					url: '../../index/index'
				});
			},
			// 
			delImg(index) {
				this.imageList.splice(index, 1)
			},
			chooseImage() {
				const count = 1 - this.imageList.length
				var that = this
				uni.chooseImage({
					count: count,
					success: (res) => {
						var tempfilepaths = res.tempFilePaths
						tempfilepaths.forEach((item, index) => {
							// 处理图片多选的状况
							if (index < count) {
								this.imageList.push({
									url: item
								})
							}
						});
						// // #ifdef APP-PLUS 
						// uni.saveFile({
						// 	tempFilePath: res.tempFilePaths[0],
						// 	success: function(saveRes) {
						// 		that.diary.cover = saveRes.savedFilePath;
						// 	}
						// });
						// // #endif
						// // #ifndef APP-PLUS
						// that.diary.cover = res.tempFilePaths[0];
						// // #endif
						that.diary.cover = res.tempFilePaths[0];
						console.log(res)
					}
				})
			}
			//
		},
		onLoad(res) {
			this.diary = getWritingDiary();
			this.setTitle();
			if (this.diary.isCompete || true) {
				this.dragBoxList = this.diary.getContent();
				this.title = this.diary.getTitle();
				if (this.diary.cover != null) {
					this.imageList.push({
						url: this.diary.cover
					});
				}
			}
			uni.showToast({
				title: "文本框双击变大，三击变小，长按删除",
				position: 'bottom',
				icon: 'none'
			})

			var _this = this;
			/* uni.getImageInfo({
				src:'/static/u=3260214634,2926460972&fm=26&gp=0.jpg',
				success(res) {
					console.log(res);
					_this.defaultCover = res.path;
				}
			}) */
		},
		onBackPress(event) {
			console.log('返回:', event);
		}
	}
</script>

<style lang="scss">
	.content {
		margin-top: -5rpx;
		box-sizing: border-box;
		display: flex;
		padding: 20rpx 40rpx;
		flex-direction: column;
		border-radius: 20rpx;
		// border: #09181F solid 5rpx;
		box-shadow: 0rpx -2rpx 10rpx #777;

		.content_title {
			margin-bottom: 10rpx;
			box-sizing: border-box;
			width: 100%;
			height: 80rpx;
			line-height: 100rpx;
			font-size: 35rpx;
			border-bottom: 1px solid #ececec;
		}

		.content_info {
			width: 100%;
			font-size: 30rpx;
			line-height: 1.8;
			height: 700rpx;
		}
	}

	.addImage-box {
		background: #F9F9F9;
		display: flex;
		flex-wrap: wrap;
		height: 500rpx;
		align-items: center;

		.addImage-item {
			position: relative;
			width: 100%;
			height: 500rpx;
			padding-top: 33.33%;
			box-sizing: border-box;
			justify-content: center;

			.close-icon {
				display: flex;
				justify-content: center;
				align-items: center;
				position: absolute;
				right: 10rpx;
				top: 10rpx;
				z-index: 2;
				width: 80rpx;
				height: 80rpx;
				border-radius: 10rpx;
				background-color: #ccc;
			}

			.image-box {
				display: flex;
				justify-content: center;
				align-items: center;
				position: absolute;
				top: 10rpx;
				bottom: 10rpx;
				left: 10rpx;
				right: 10rpx;
				border-radius: 10rpx;
				overflow: hidden;

				.plusPhoto {
					border-radius: 50%;
					padding: 20rpx;
					background-color: #bbb;
				}

				image {
					width: 100%;
					height: 100%;
				}
			}
		}
	}

	.tabBar {
		border: #09181F 1rpx solid;
		padding: 20rpx 0;
		background-color: white;
		position: fixed;
		z-index: 100;
		width: 100%;
		bottom: 0;
		display: flex;
		flex-direction: row;
		align-items: flex-end;
		justify-content: space-between;
	}

	.tabBar image {
		height: 50rpx;
		width: 50rpx;

	}

	.tabBar .draft {
		margin-right: 20rpx;
	}

	.tabBar .text {
		margin-right: 0;
		margin-left: 20rpx;
		border: #09181F solid 1px;
		height: 60rpx;
		width: 500rpx;
		line-height: 60rpx;
		border-radius: 100rpx;
		font-size: 20rpx;
		background-color: #f2f2f2
	}

	.textArea {
		/* left: -5rpx;
		top: -5rpx; */
		// border: #008000 solid;
		position: absolute
	}

	movable-view {
		display: flex;
		justify-content: center;
		width: 100rpx;
		height: 100rpx;
	}
</style>
