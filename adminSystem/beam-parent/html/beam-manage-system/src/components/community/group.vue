<template>
    <div class="container-box">
        <div v-for="(o, index) in 12" class="group-box">
            <el-image class="img-box" src="https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg"></el-image>
            <div class="text-box">某某某某团购群</div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "group",
        data() {
            return {
            };
        }
    }
</script>

<style scoped>
    .container-box{
        width: 100%;
        height: 100%;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
        overflow-y: auto;
    }
    .group-box{
        display: flex;
        width: 260px;
        height: 280px;
        padding: 5px;
        flex-flow:column wrap;
        /*border:1px solid #000;*/
    }
    .img-box{
        display: flex;
        width: 250px;
        height: 250px;
    }
    .text-box{
        text-align: center;
        line-height: 30px;
        font-size: 15px;
    }

</style>
