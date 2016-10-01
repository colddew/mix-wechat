# description
wechat public platform development

# add news manually

a) upload image

```zsh
curl -F media=@test.png 'https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=<access_token>'

{"url":"http://mmbiz.qpic.cn/mmbiz_png/<random_string>/0"}
```

b) upload image thumb

```zsh
curl -F media=@test.png 'https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=<access_token>=thumb'

{"media_id":"<media_id>","url":"http://mmbiz.qpic.cn/mmbiz_png/<random_string>/0?wx_fmt=png"}
```

c) add news

```zsh
curl -XPOST 'https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=<access_token>' -d '
{
  "articles": [{
       "title": "<title>",
       "thumb_media_id": "<thumb_media_id>",
       "author": "<author>",
       "digest": "<digest>",
       "show_cover_pic": 0,
       "content": "<html_content>",
       "content_source_url": "<content_source_url>"
    }
 ]
}'

{"media_id":"_MzjDMw0urnYOx6NBiHLG-9H1jLAdJlBARbkcz4M8ys"}
```

d) get news

```zsh
curl -XPOST 'https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=<access_token>' -d '{"media_id":"_MzjDMw0urnYOx6NBiHLG-9H1jLAdJlBARbkcz4M8ys"}'

{
    "news_item": [
        {
            "title": "<title>",
            "author": "<author>",
            "digest": "<digest>",
            "content": "<html_content>",
            "content_source_url": "<content_source_url>",
            "thumb_media_id": "<thumb_media_id>",
            "show_cover_pic": 0,
            "url": "http://mp.weixin.qq.com/s?__biz=<random_string>==&mid=100000016&idx=1&sn=<random_string>#rd",
            "thumb_url": "http://mmbiz.qpic.cn/mmbiz_png/<random_string>/0?wx_fmt=png"
        }
    ],
    "create_time": 1473149647,
    "update_time": 1473149647
}
```

# startup
nohup java -jar wechat-plant.jar &
