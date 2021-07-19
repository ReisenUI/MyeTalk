<template>
	<view class="root">
		<view class="line1">
			<view class="card" @tap='chooseA'>
				<image
					src="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=278530408,3811144136&fm=26&gp=0.jpg">
				</image>
                A
			</view>
			<view class="card" @tap='chooseB'>
				<image
					src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3412491959,188725011&fm=26&gp=0.jpg">
				</image>
				B
			</view>
		</view>
		<view class="line2">
			<view class="card" @tap='chooseC'>
				<image
					src="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1122020366,3176355633&fm=26&gp=0.jpg">
				</image>
				C
			</view>
			<view class="card" @tap='chooseD'>
				<image
					src="https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=226499281,2284342982&fm=26&gp=0.jpg">
				</image>
				D
			</view>
		</view>
		<view class="answer" v-if="chooseid==1">
		    选这张牌表示你未来十年会比较富贵，
			你的性格比较的平易近人，和朋友相处的也比较好，
			是一个比较有人缘的人。
			你做事也比较的细心和踏实，也不容易犯错误而导致事情失败。
			你特别有一点优点就是你非常的谦虚，
			不容易被胜利冲昏头脑，你的努力都会有成果的。
			未来十年你会凭借自己的努力变得强大，
		    有一场富贵在等你。
		</view>
		<view class="answer" v-if="chooseid==2">
			选这张牌表示你未来十年会非常富贵，你最经会经历一些转变。
			可能时学业，可能是工作，或者是情感。
			也许这些突然间的改变会让你茫然失措。
			但是谁不是这样呢？不经过这样一番经历是不能够成熟的，
			你性格中的积极因素将会被激活，你会在这里获得成长，
			未来十年，你会非常富贵。
		</view>
		<view class="answer" v-if="chooseid==3">
			选这张牌表示你未来十年会极为富贵，你的性格慷慨大方，而且为人没有太多的计算。
			朋友们和你相处起来也会非常的自然，你最满足的还是心灵上的获得，你帮别人，别人帮你。
			未来你会在众多人脉的帮助下，会过的极为顺利，你的未来十年会极为富贵。
		</view>
		<view class="answer" v-if="chooseid==4">
			选这张牌表示你在未来十年中获得的财富并不多，可能最近你会比较茫然，
			是因为不知道现在从事的事情还有什么价值。
			这可能是过多的重复，导致你失去了当初的热情。
			也可能是暂时没有回报导致你的心理出现了懈怠。
			这些其实都是暂时的，只要你心里的想法一转变，你的气势就会提升上，
			慢慢得到你想要的都会在你的努力下来到你的身边。
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
