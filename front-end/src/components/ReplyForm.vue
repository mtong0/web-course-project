<template>
  <div class="modal fade" id="reply" tabindex="-1" aria-labelledby="replyFormLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="replyFormLabel">Reply</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body mb-3">
          <div class="input-group">
            <span class="input-group-text">Username</span>
            <input type="text" class="form_control" placeholder="example@eamil.com" aria-label="Username" id="username">
          </div>
          <div class="input-group mb-3" style="margin-top: 5px">
            <span class="input-group-text">Password</span>
            <input type="password" class="form_control" aria-label="Password" id="password">
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" v-on:click="login()">Log in</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

</script>