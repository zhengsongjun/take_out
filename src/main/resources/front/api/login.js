function loginApi(data) {
    return $axios({
      'url': '/user/login',
      'method': 'post',
      data
    })
  }


function registerApi(data) {
    return $axios({
        'url': '/user/register',
        'method': 'post',
        data
    })
}

function loginoutApi() {
  return $axios({
    'url': '/user/loginout',
    'method': 'post',
  })
}

  