<template>
  <div style="width: 60%; margin: auto">
    <div>
      <div class="mb-3">
        <label for="questionTitle" class="form-label">Question Title</label>
        <input type="email" class="form-control" id="questionTitle" v-model="question['name']">
      </div>
      <div>
        <button class="btn btn-primary" @click="submit">Ask</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data(){ return{
    question: {
      posterId: localStorage.getItem("userId"),
      name: ""
    },
  }},
  methods: {
    submit(){
      let _this = this;
      const xHttp = new XMLHttpRequest();
      xHttp.open("POST", "/api/question/post");
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      xHttp.onreadystatechange = function () {
        if (this.readyState === 4) {
          if (this.status === 200) {
            alert("successfully!");
          }
        }
      }
      xHttp.onload = function () {
        _this.$router.push({path:"/"});
      }
      xHttp.send(JSON.stringify(this.question));
    }
  }

}
</script>