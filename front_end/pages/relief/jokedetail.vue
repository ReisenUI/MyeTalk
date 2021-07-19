<template>
	<view class="root">
		<view class="title">{{title}}</view>
		<view class="border">
			<view v-if="id==0" class="content">
				有个伞兵,胆特别小.一天晚上在完成任务时,他为了让陆地上的人能看到他,就在自己的身上安了许多闪闪发亮的灯泡,可是这个笨蛋偏偏落进了别人的院子里,院子的主人是一位六十多岁的老妇人,她听见声响以后就把门打开,看看到底发生了什么事.

				跳伞兵看见有人开门出来,于是他拖着满身的灯泡,走过去很礼貌的问那位妇人: "太太.请问我这是在什么地方?"
				那妇人缩成一团望着他,用颤抖的声音说: "地...地...地球..." </view>
			<view v-if="id==1" class="content">
				一个男生暗恋一个女生很久了。一天自习课上，男生偷偷的传了小纸条给女生，上面写着“其实我注意你很久了”。不一会儿，女生传了另一张纸条，男生心急火燎的打开一看“拜托你不要告诉老师，我保证以后再也不嗑瓜子了”。。。。。。男生一脸懵逼
			</view>
			<view v-if="id==2" class="content">
                本人近视，那天看到前面一女拎着一个白色打底青色流苏状点缀的包包特别好看，感觉好配自己买的湖水绿的连衣裙，遂跑上去说：“你的包包好好看，哪儿买的？”
                结果走近一看：是一个塑料袋里面挂了两把大葱。
			</view>
			<view v-if="id==3" class="content">
				我妈打电话给我说：儿子！快来医院，你姐姐要生了！！
				我说：好 ！顺便问了一下是男还是女。 
				我妈说：现在还不知道，所以不知道你是当叔叔还是阿姨。。。
			</view>
			<view v-if="id==4" class="content">
				去逛商场，看到一个专营店的衣服很漂亮，就把外套一脱交给服务员开始各种试穿，试穿了很多衣服一看价格还是觉得算了，回头问服务员：我的外套呢？
				服务员找了很久，呆呆地看着我说：刚才客人太多，好像。。。好像是被我卖了。。。
			</view>
			<view v-if="id==5" class="content">
				老婆逗儿子说：“我和你爸再给你生个妹妹咋样？”
				儿子：“我知道你们这是图省事！ ”
				望着我们疑惑的样子，他继续解释道：“不就是等我们长大了直接结婚吗？到时你们就省得再费事给我找老婆了！！ ”
			</view>
			<view v-if="id==6" class="content">
				去参加婚礼，安排座位的是我哥们儿，他说我未婚，要给我安排一桌单身的。
				我那个激动啊，心想着指不定还能有个单身女神什么的。。。
				后来，把我安排一桌都是小朋友的位置上，还TM让我照顾Ta们都吃好！！！
			</view>
			<view v-if="id==7" class="content">
				便秘，好难受。
				七岁的儿子说：“爸爸，我有办法！ ”
				我说：“啥办法？快说！ ”
				儿子说：“咱们买一个可以转的马桶，你就坐在上边，一按电钮，不就甩出来了吗？”
				我。。。
			</view>
		</view>
	</view>
</template>

<script>
	export default {

		data() {
			return {
				id: null,
				title: null
			}
		},
		onLoad: function(option) {
			this.id = option.id,
				this.title = option.title
		},
		methods: {

		}
	}
</script>

<style>
	page {
		background: #fcfcfc;
	}

	.title {
		width: 100%;
		height: 100rpx;
		margin-left: 70rpx;
		margin-top: 20rpx;
		color: #4c9acc;
		font-weight: 1000;
	}

	.root {
		display: flex;
		align-items: center;
		flex-direction: column;
		justify-content: center;
	}

	.content {
		width: 80%;
		height: auto;
		background: #fff;
		margin: 10rpx 20rpx;
		color: #4c9acc;
	}

	.border {
		width: 90%;
		background: #fff;
		display: flex;
		flex-direction: column;
		align-items: center;
		height: auto;
		border-radius: 20rpx;
	}
</style>
