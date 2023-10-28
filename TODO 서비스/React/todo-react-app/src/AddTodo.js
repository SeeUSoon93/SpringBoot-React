import { Button, Grid, TextField } from "@mui/material";
import React, { useState } from "react";

const AddTodo = (props) => {
    // 사용자의 입력을 저장할 객체
    const [item, setItem] = useState({ title: "" });
    const addItem = props.addItem;
    

    // onInputChange함수 - 사용자가 인풋필드에 키를 입력할 때마다 실행. 담긴 문자열을 자바스크립트 객체에 저장
    const onInputChange = (e) => {
        setItem({ title: e.target.value });
        console.log(item);
    };

    // onButtonClick 함수 - 버튼을 클릭할떄 실행. onInputChange가 저장하고 있던 문자열을 추가
    const onButtonClick = () => {
        addItem(item);
        setItem({ title: "" });
    };

    // enter키 처리 함수
    const enterKeyEventHandler = (e)=>{
        if(e.key === "Enter"){
            onButtonClick();
        }
    };

    return (
        <Grid container style={{ marginTop: 20 }}>
            <Grid xs={11} md={11} item style={{ paddingRight: 16 }}>
                <TextField placeholder="ToDo를 추가 하세요" fullWidth onChange={onInputChange} onKeyDown={enterKeyEventHandler} value={item.title} />
            </Grid>
            <Grid xs={1} md={1} item>
                <Button fullWidth style={{ height: '100%' }} color="secondary" variant="outlined" onClick={onButtonClick}>+</Button>
            </Grid>
        </Grid>
    );
}

export default AddTodo;