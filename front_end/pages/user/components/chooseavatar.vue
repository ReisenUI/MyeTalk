<template>
	<view>
		<view class="tips">
			<view>那么</view>
			<view>你喜欢哪张头像呢</view>
		</view>
		<view>
			<swiper class="swiper" indicator-dots=true>
				<swiper-item v-for="i in 2" :key=i>
					<view class="avatarItem" v-for="j in 3" :key=j>
						<view v-for="(item) in getItem(i,j)" :key=item.id
							:style="{'color': item.fontColor[item.type], 'background-color': item.backgroundColor[item.type]}"
							class="avatarItemView" @tap="chooseAvatar(item)">
							<image :src="baseUrl+item.icon[item.type]+`.png`" mode="heightFix"></image>
							<view>{{item.avatar}}</view>
						</view>
					</view>
				</swiper-item>
			</swiper>
			<button @tap="saveAvatar()"
				:style="{'width': 'fit-content', 'background-color': buttonColor, 'color': fontColor, 'border-radius': '100rpx', 'padding': '10rpx 60rpx', 'font-size': '35rpx', 'margin-top' : '100rpx', 'border': 'red solid 0rpx'}">就决定是你了</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				baseUrl: '../../../static/avatar/',
				name: '',
				lineIndex: 0,
				avatar:'',
				avatars: [], // 存放已选择的活动  {"activity": "", "i": 0, "j": 0}
				slogan: '……',
				buttonColor: '#F0F0F0',
				fontColor: '#787878',
				avatarList: [
					[
						[{
								"id": 0,
								"avatar": "小天使",
								"icon": [
									"angel",
									"angel"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 1,
								"avatar": "大哥哥",
								"icon": [
									"brother",
									"brother"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 2,
								"avatar": "甲男孩",
								"icon": [
									"cp_boy",
									"cp_boy"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							}
						],
						[{
								"id": 3,
								"avatar": "麋鹿",
								"icon": [
									"deer",
									"deer"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 4,
								"avatar": "乙男孩",
								"icon": [
									"friend_boy",
									"friend_boy"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 5,
								"avatar": "甲女孩",
								"icon": [
									"friend_girl",
									"friend_girl"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							}
						],
						[{
								"id": 6,
								"avatar": "姜饼",
								"icon": [
									"gingerbread",
									"gingerbread"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 7,
								"avatar": "乙女孩",
								"icon": [
									"girl",
									"girl"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 8,
								"avatar": "小精灵",
								"icon": [
									"idou",
									"idou"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							}
						]
					],
					[
						[{
								"id": 9,
								"avatar": "小男孩",
								"icon": [
									"little_boy",
									"little_boy"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 10,
								"avatar": "男人",
								"icon": [
									"man",
									"man"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 11,
								"avatar": "丙男孩",
								"icon": [
									"my_boy",
									"my_boy"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							}
						],
						[{
								"id": 12,
								"avatar": "丙女孩",
								"icon": [
									"my_girl",
									"my_girl"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 13,
								"avatar": "雪人",
								"icon": [
									"snowman",
									"snowman"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							},
							{
								"id": 14,
								"avatar": "男学生",
								"icon": [
									"student",
									"student"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							}
						],
						[{
								"id": 15,
								"avatar": "女学生",
								"icon": [
									"student_girl",
									"student_girl"
								],
								"backgroundColor": [
									"white"
								],
								"fontColor": [
									"#808080",
									"black"
								],
								"type": 0
							}
							
						]
					]
				]
				}
		},
		methods: {
				getItem(i, j) {
					return this.avatarList[i - 1][j - 1];
				},
				chooseAvatar(item) {
					// 满了就替换，没满就插入
					let max = 1; // 每次可以选择的最大数量
					if (this.avatars.indexOf(item) !== -1) {
						// let temp = this.avatars.indexOf(item);
						// item.type = 0;
						// this.avatars.splice(temp, 1);
						// if (this.lineIndex < temp)
						// 	return;
						// this.lineIndex--;
						// if (this.lineIndex < 0) {
						// 	this.lineIndex += max;
						// }
						// console.log("重复");
		
						return;
					}
		
					item.type = 1;
					if (this.avatars.length == max) {
						this.avatars[this.lineIndex].type = 0;
						this.avatars.splice(this.lineIndex, 1, item) // 索引位置（可负），替换次数，替换对象
					} else {
						this.avatars.splice(this.lineIndex, 0, item)
					}
					this.lineIndex++;
					this.lineIndex %= max;
					// 按钮变色 可优化
					this.buttonColor = "#09181F";
					this.fontColor = "white";
					// 可优化
				},
				saveAvatar(){
					const _this=this
					uni.request({
						// url: 'http://zk123.top:9001/login',
						url: 'https://zk123.top/mye/people/avatar',
						header: {'content-type':'application/x-www-form-urlencoded',
						Authorization:uni.getStorageSync('token')},
						method: 'POST',
						data: {avatar:"baseUrl+_this.icon[item.type]+`.png`"},
						dataType: 'json',
						success: (res) => {
							
							console.log(res);
						},
						fail: () => {
							
						},
						complete: () => {}
					});
					}
		
			},
			computed: {
		
			},
			onLoad(e) {
				
			}
		
}
</script>

<style>
	* {
		//overflow: hidden;
	}
	
	.tips {
		/* border: #007AFF solid; */
		box-sizing: border-box; // 解决width: 100% 下padding溢出问题
		padding-top: 50rpx;
		padding-left: 50rpx;
		width: 100%;
	}
	
	.tips view {
		box-sizing: border-box;
		/* border: red solid; */
		margin: 20rpx 0;
		font-style: oblique;
		width: fit-content;
		font-size: 30rpx;
	}
	
	.swiper {
		padding-top: 30rpx;
		height: 800rpx;
	}
	
	.avatarItem {
		/* border: #007AFF solid; */
		margin: 30rpx 50rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between
	}
	
	
	.avatarItem image {
		/* flex: 1; */
		/* border: #007AFF solid; */
		border-radius: 15rpx;
		height: 200rpx;
		padding: 0;
	}
	
	.avatarItemView {
		display: flex;
		flex-direction: column;
		display: flex;
		box-shadow: -1rpx 1rpx 11rpx -1rpx rgba(9, 24, 31, 0.47);
		justify-content: center;
		align-items: center;
		height: 200rpx;
		width: 150rpx;
		border-radius: 15rpx;
		/* border: #007AFF solid; */
		margin: 0 20rpx;
		padding: 0;
	}
</style>
