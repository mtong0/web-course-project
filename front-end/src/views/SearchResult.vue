<template>
  <div style="width: 60%; margin: auto">
    <div class="card" style="width: 100%; cursor: pointer" v-for="item in questionCards">
      <div class="card-body">
        <h5 class="card-title" style="display: inline-block" @click="goToQuestion(item['questionId'])" >{{item["content"]}}</h5>
        <button @click="markGoodQuestion(item['questionId'])">Good Question: {{item["questionVoteNum"]}}</button>
        <h6 class="card-subtitle mb-2 text-muted">{{item["answerNickName"]}}</h6>
        <p style="height: 2rem; overflow: hidden; text-overflow: ellipsis;white-space: nowrap;"
           class="card-text" @click="goToQA(item['questionId'], item['answerId'])">{{item["answerContent"]}}</p>
        <div v-show="item['answerNickName']!==null">
          <label for="vote">Vote Number:</label><span id="vote" style="margin-right: 1rem;">{{item["answerVoteNum"]}}</span>
          <label for="reply">Reply Number:</label><span id="reply">{{item["answerReplyNum"]}}</span>
        </div>
        <div v-show="item['answerNickName']===null">
          <span>No Answer Yet!</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data(){return {
    questionCards: {}
  }},
  mounted() {
    this.search();
  },
  methods: {
    goToQuestion(questionId){
      this.$router.push({path: "/question?questionId="+questionId});
    },
    goToQA(questionId, answerId) {
      this.$router.push({path: "/question?questionId="+questionId+"&answerId="+answerId});
    },
    markGoodQuestion(questionId) {
      console.log(questionId)
      const xHttp = new XMLHttpRequest();
      xHttp.open("GET", "/api/question/vote?userId="+localStorage.getItem("userId")
          +"&questionId="+questionId);
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      let _this = this;
      xHttp.onload = function () {
        alert(this.response["response"]);
      }
      xHttp.send();
    },
    search(){
      let searchString = localStorage.getItem("searchString");
      let noiseWords = [
        "a", "the", "of", "and", "to", "in", "of", "about",
        "is", "am", "are", "that", "this", "these","those",
        "by", "with", "were", "was", "he", "her", "they", "it",
        "or", "she", "I", "can", "could", "be", "some", "from", "if",
        "so", "do", "does", "did", "then", "into", "we", "been",
        "my", "than", "an", "have", "had", "as",  "but", "much", "more",
        "out", "must", "know", "people", "other", "good", "only", "its", "over",
        "think", "us", "him", "his", "her", "their", "back", "just", "up", "down",
        "go", "went", "take", "toke", "put", "get", "got", "by", "because","at",
        "say", "said", "any", "on", "nor", "not", "im", "don't", "can't", "couldn't",
        "should", "shouldn't", "didn't", "wasn't", "weren't", "would", "me", "there",
        "very", "ago", "year", "one", "for", "no", "day", "what", "which", "how", "where",
        "who", "whom", "left", "right", "every", "most", "like", "one", "two", "i", "make",
        "really", "made", "has", "nothing", "also", "will", "them", "took", "when", "open",
        "alone", "hey", "going", "outside", "inside", "being", "someone", "somebody", "due",
        "actually", "may", "might", "possible", "you", "too", "after", "before", "getting"
      ];
      let searchTokens = searchString.split(" ");
      let filteredString = "";
      for (let i = 0; i < searchTokens.length; i++) {
        if (!noiseWords.includes(searchTokens[i].toLowerCase())) {
          filteredString += searchTokens[i].toLowerCase() + " ";
        }
      }
      console.log("the filtered search string is: " + filteredString);
      let filteredStringTokens = filteredString.split(" ");
      let regex = ""
      for (let i = 0; i < filteredStringTokens.length-1; i++) {
          regex += "("+ filteredStringTokens[i]+")|";
      }
      regex = regex.substring(0, regex.length-1);
      let sql =
      "SELECT * FROM answer JOIN\n" +
      "    (SELECT\n" +
      "           a.id,\n" +
      "           t.match_points*10 + a.rank_point + q2.rank_point rank_points,\n" +
      "           RANK() OVER (PARTITION BY a.question_id\n" +
      "               ORDER BY t.match_points*15 + a.rank_point + q2.rank_point DESC) a_rank\n" +
      "    FROM answer a\n" +
      "        JOIN\n" +
      "            (SELECT\n" +
      "                q.id question_id,\n" +
      "                a.id answer_id,\n" +
      "                substrCount(lower(q.name),"+regex +") * 2 +\n" +
      "                substrCount(lower(a.content),"+ regex +") match_points\n" +
      "            FROM question q\n" +
      "                JOIN answer a on q.id = a.question_id\n" +
      "            ORDER BY match_points DESC) t ON a.id = t.answer_id\n" +
      "    JOIN question q2 on a.question_id = q2.id\n" +
      "    WHERE t.match_points*10 + a.rank_point + q2.rank_point  > 0\n" +
      "    ORDER BY rank_points DESC) as tt\n" +
      "USING (id)\n" +
      "WHERE a_rank = 1"
      console.log(sql);
      let userId = localStorage.getItem("userId");
      const xHttp = new XMLHttpRequest();
      xHttp.open("POST", "/api/search/normalSearch");
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      let _this = this;
      xHttp.onload = function () {
        _this.questionCards = this.response["questionCards"];
        console.log(_this.questionCards);
      }
      xHttp.send( JSON.stringify({searchString: filteredString, userId: userId}));
    }
  },
}
</script>