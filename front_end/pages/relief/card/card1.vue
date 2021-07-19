<template>
	<view class="root">
		<view class="line1">
			<view class="card" @tap='chooseA'>
				<image
					src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2098909463,3263866105&fm=26&gp=0.jpg">
				</image>
                A
			</view>
			<view class="card" @tap='chooseB'>
				<image
					src="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1466138225,3405337522&fm=26&gp=0.jpg">
				</image>
				B
			</view>
		</view>
		<view class="line2">
			<view class="card" @tap='chooseC'>
				<image
					src="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=453291655,3572345448&fm=26&gp=0.jpg">
				</image>
				C
			</view>
			<view class="card" @tap='chooseD'>
				<image
					src="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2686703616,2485409170&fm=26&gp=0.jpg">
				</image>
				D
			</view>
		</view>
		<view class="answer" v-if="chooseid==1">
		    选这张牌表示你会收到财富上的喜讯，当下的你几乎把所有精力都放在生财上面。
			你清楚的认识到财富的重要性，为了家人生活得更好，一直在孜孜不倦的工作。
			所谓一分耕耘一份收获，你的汗水不会白流。
			你目标明确，而且你身上有坚韧不拔的毅力，凭借你的付出，终将能够赢得财富上的惊喜。
		</view>
		<view class="answer" v-if="chooseid==2">
			选这张牌表示你会收到职场上的喜讯，踏实而真诚的你，无论生活还是工作，都显得有条不紊。
			尤其在职场上，你能够用自己的真心实意，做好本职工作，在赢得同事们认可的同时，也获得领导的关注。
			接下来的时间中，你的事业将会一帆风顺，被好运包围。
			由于你平时的努力，你将能够担负重任，一不小心，或许就能获得升职加薪的机会。
		</view>
		<view class="answer" v-if="chooseid==3">
			选这张牌表示你将获得生活上的满足。
			能够时刻保持乐观心态的你，心里面几乎没有阴暗的想法，凡事会往好的方面想。
			也能与人愉快的交流，在工作上全力以赴做事。
			所以你总能获得幸运之神的眷顾，好运随时而来。
			你能够将自己的生活打理好，也满足于眼前的生活。
			即使会遭遇到一些小挫折，凭借你的心态，往往不会受其影响，只会让你更加珍惜当下的美好生活。
		</view>
		<view class="answer" v-if="chooseid==4">
			选这张牌表示你将获得桃花上的提升。
			细心体贴的你，在人际交往中时常能够获得好人缘。
			往往走到哪里，都能收到大家的欢迎。
			尤其在异性的心目中，你温柔不失浪漫，体贴也具备梦幻，很容易赢得对方的青睐。
			深受大家欢迎的你，似乎走到哪里都能让人产生好感，尤其在异性的心里，更容易泛起涟漪。
			所以，你的爱情桃花栩栩生辉，时常能够在不经意间就突然而至。
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
