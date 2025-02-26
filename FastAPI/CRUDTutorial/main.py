from typing import Optional
from fastapi import FastAPI
from fastapi.params import Body
from pydantic import BaseModel
from random import randrange 

app = FastAPI()

class Post(BaseModel):
    title: str
    content: str
    published: bool = True
    rating: Optional[int] = None

my_posts = [
    {"title": "Post 1", "content": "This is a post", "published": True, "rating": 5, "id": 1},
    {"title": "Post 2", "content": "This is another post", "published": True, "rating": 4, "id": 2},
    {"title": "Post 3", "content": "This is a post", "published": False, "rating": 3, "id": 3},
    ]
 
def find_post(id):
    for p in my_posts:
        if p["id"] == id:
            return p

@app.get("/")
def root():
    return {"message": "Oussama"}

@app.get("/posts")
def get_posts():
    return {"data": my_posts}

@app.post("/posts")
def create_post(post: Post):
    print(post.model_dump())
    post_dic = post.model_dump()  
    post_dic["id"] = randrange(0, 10000000)
    my_posts.append(post_dic)
    return {"data": post_dic}

@app.get("/posts/{id}")
def get_post_by_id(id:int):
    post = find_post(id=id)  
    return {"data": post}