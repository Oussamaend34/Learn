from typing import Optional
from fastapi import FastAPI, Response, status, HTTPException
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
def find_post_index(id):
    for i, p in enumerate(my_posts):
        if p["id"] == id:
            return i

@app.get("/")
def root():
    return {"message": "Oussama"}

@app.get("/posts")
def get_posts():
    return {"data": my_posts}

@app.post("/posts", status_code = status.HTTP_201_CREATED)
def create_post(post: Post):
    print(post.model_dump())
    post_dic = post.model_dump()  
    post_dic["id"] = randrange(0, 10000000)
    my_posts.append(post_dic)
    return {"data": post_dic}

@app.get("/posts/latest")
def get_latest_post():
    post = my_posts[-1]
    return {"data": post} 

@app.get("/posts/{id}")
def get_post_by_id(id:int): 
    post = find_post(id=id) 
    if not post:
        raise HTTPException(
            status_code=404,
            detail=f"Post with id {id} not found"
            )
    return {"data": post}

@app.delete("/posts/{id}", status_code = status.HTTP_204_NO_CONTENT)
def delete_post_by_id(id:int):
    index = find_post_index(id=id)
    if index is None:
        raise HTTPException(
            status_code=404,
            detail=f"Post with id {id} not found"
            )
    my_posts.pop(index)
    return Response(status_code=status.HTTP_204_NO_CONTENT)