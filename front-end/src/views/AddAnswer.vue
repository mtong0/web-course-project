<template>
  <div style="width: 60%; margin:auto">
    <div>
      <span>{{question["content"]}}</span>
    </div>
    <hr>
    <div>
      <span>My Answer</span>
    </div>
    <textarea cols="30" rows="10" v-model="answer['content']"></textarea>
    <button class="btn btn-primary" @click="post">Post</button>
  </div>
</template>

<script>
export default {
  name: "AddAnswer",
  data() {return {
    question: {},
    answer: {
      questionId: 0,
      content: "",
      posterId: localStorage.getItem("userId"),
    }
  }},
  mounted() {
    let questionId = this.$route.query.questionId;
    const xHttp = new XMLHttpRequest();
    xHttp.open("GET", "/api/question/"+questionId);
    xHttp.setRequestHeader("Accept", "application/json");
    xHttp.setRequestHeader("Content-Type", "application/json");
    xHttp.responseType = "json";
    let _this = this;
    xHttp.onload = function () {
      _this.question = this.response["question"];
      console.log(_this.question);
    }
    xHttp.send();
  },
  methods: {
    post(){
      this.answer.questionId = this.question["id"];
      const xHttp = new XMLHttpRequest();
      xHttp.open("POST", "/api/answer/post");
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      xHttp.onreadystatechange = function () {
        if (this.readyState === 4) {
          if (this.status === 200) {
            alert("Successfully!");
          }
        }
      }
      xHttp.send(JSON.stringify(this.answer));
    }
  }
}
</script>

<style scoped>

</style>