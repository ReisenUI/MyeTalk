<template>
	<view class="root">
		<view class="line1">
			<view class="card" @tap='chooseA'>
				<image
					src="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=53364151,3850031579&fm=26&gp=0.jpg">
				</image>
                A
			</view>
			<view class="card" @tap='chooseB'>
				<image
					src="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3627650501,767659824&fm=26&gp=0.jpg">
				</image>
				B
			</view>
		</view>
		<view class="line2">
			<view class="card" @tap='chooseC'>
				<image
					src="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1562729546,2814358598&fm=26&gp=0.jpg">
				</image>
				C
			</view>
			<view class="card" @tap='chooseD'>
				<image
					src="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=847855016,1262855705&fm=26&gp=0.jpg">
				</image>
				D
			</view>
		</view>
		<view class="answer" v-if="chooseid==1">
		    选这张牌表示你的结婚年龄是21、25、26岁，你是一个阳光的人，
			给人一种轻松舒服的感觉。因此有很多异性喜欢你，你结婚还是比较早的。
			对于未来你有一种积极的态度，又要两个人在一起，
			你就会和对方一牵手就是一辈子，
			你这种乐观的态度会让你婚后生活十分幸福。
		</view>
		<view class="answer" v-if="chooseid==2">
			选这张牌表示,你的结婚年龄是22、27、29岁，
			你是一个很有气质，也很端庄的人，
			特别注重自己的穿着打扮。
			还有你的情商也很高，懂得拿捏分寸，所以在异性眼中你很有魅力。
			你对待感情是很勇敢也很有主见的，在你22岁之前遇到的那个人，
			你有很大的冲动想要与TA一直在一起，
			一旦到了27岁左右你就有一种强烈的想要结婚的感觉，并且也能和满意的对象走向婚姻。
		</view>
		<view class="answer" v-if="chooseid==3">
			选这张牌表示你的结婚年龄是25、26、33岁，你是一个看上去比较高冷的人，在爱情中
			你会占据主导地位，给对方一种比较强势的感觉。
			你也许是爱着对方，但是却不断的试探TA、考研TA。
			你觉得这样做无可厚非，但是真的可能成为毁坏你们爱情的关键。
			你青春期的时候追求你的人很多，但到了接近结婚的年龄时，却发现身边的人渐渐的越来越少。
		</view>
		<view class="answer" v-if="chooseid==4">
			选这张牌表示你的结婚年龄是20、28、31岁，你是一个很细心有很体贴的人。
			你很容易感动，懂事又善解人意。你对待周遭的事情会多一些自己的感受，
			但是也很容易情绪大爆发，你真正走心的那场恋爱是20岁左右时发生的。
			那种干干净净想要一直走下去的感觉让你久久不能忘怀。
			你真正形成成熟的婚姻和恋爱观是在28岁到31岁，这两年也是你结婚的年龄
		</view>
	</view>
</template>

<script>
	export default{
		data() {
			return {
		        chooseid:null
			}
		},
		methods: {
			chooseA(){
			if(this.chooseid!==null){
				this.chooseid=null
			}
			else{
				this.chooseid=1;
			}
			},
			chooseB(){
			if(this.chooseid!==null){
				this.chooseid=null
			}
			else{
				this.chooseid=2
			}
			},
			chooseC(){
			if(this.chooseid!==null){
				this.chooseid=null
			}
			else{
				this.chooseid=3;
			}
			},
			chooseD(){
			if(this.chooseid!==null){
				this.chooseid=null
			}
			else{
				this.chooseid=4
			}
			}
			}
	}
</script>

<style lang="scss">
	page{
		background: #463267;
	}
	.answer{
		color: #fff;
		font-size: 40rpx;
		display: flex;
		justify-content: center;
		margin: 0 20rpx;
		padding: 0 20rpx;
		border: 1px solid #fff;
	}
	.root {
		background: #463267;
		width: 100%;
		height: 100%;
	}

	.line1 {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin-bottom: 10rpx;
		padding: 0 20rpx;
        margin-top: 20rpx;

	}

	.line2 {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		padding: 0 20rpx;


	}

	.card {
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		justify-content: center;
		align-items: center;
		color: white;
		padding: 0 20rpx;
		width: 50%;
		height: 400rpx;
		image {
			width: 100%;
			height: 400rpx;
		}
	}
</style>
