import { Container, List, Paper } from "@mui/material";
import React, { useEffect, useState } from 'react';
import AddTodo from "./AddTodo";
import './App.css';
import Todo from './Todo';
import { call } from "./service/ApiService";


function App() {
  const [items, setItems] = useState([]);

  // todo API를 사용해 리스트 초기화
  useEffect(() => {
    call("/todo","GET",null)
    .then((response)=>setItems(response.data))
    .catch((error) => console.error(error));
    /*  기존 코드 - call함수 사용 전
    const requestOptions = {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    };


    // fetch 함수
    fetch("http://localhost:8080/todo", requestOptions)
      .then((response) => response.json())
      .then(
        (response) => {
          setItems(response.data);
        },
        (error) => { }
      );
    */
  },[])

  // addItem 함수
  const addItem = (item) => {
    call("/todo","POST",item)
    .then((response)=>setItems(response.data));
    /*item.id = "ID-" + items.length; //key를 위한 id
    item.done = false; //done 초기화
    // 업데이트는 반드시 setItems로 하고 새 배열을 만들어야 한다.
    setItems([...items, item])
    console.log("items : ", items);*/
  };

  // delete 함수
  const deleteItem = (item) => {
    call("/todo","DELETE",item)
    .then((response)=>setItems(response.data));
    /*
    // 삭제할 아이템을 찾기
    const newItems = items.filter(e => e.id !== item.id);
    // 삭제할 아이템을 제외한 아이템을 배열 저장
    setItems([...newItems]);*/
  };

  // edit함수
  const editItem = (item) => {
    call("/todo","PUT", item)
    .then((response)=> setItems(response.data));
    /*setItems([...items]);*/
  };

  let todoItems = items?.length > 0 && (
    <Paper style={{ margin: 16 }}>
      <List>
        {items.map((item) => (
          <Todo item={item} key={item.id} deleteItem={deleteItem} editItem={editItem} />
        ))}
      </List>
    </Paper>
  );
  return (<div className="App">
    <Container maxWidth="md">
      <AddTodo addItem={addItem} />
      <div className="TodoList">{todoItems}</div>
    </Container>
  </div>);
}

export default App;
