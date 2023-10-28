import DeleteOutlined from "@mui/icons-material/DeleteOutlined";
import { Checkbox, IconButton, InputBase, ListItem, ListItemSecondaryAction, ListItemText } from "@mui/material";
import React, { useState } from "react";

const Todo = (props) => {
    const [item, setItem] = useState(props.item);
    const [readOnly, setReadOnly] = useState(true);
    const deleteItem = props.deleteItem;
    const editItem = props.editItem;

    // delete 함수
    const deleteEventHandler = () => {
        deleteItem(item);
    };

    // 제목 클릭시 수정가능하도록 ReadOnly 끄기
    const turnOffReadOnly=()=>{
        setReadOnly(false);
    };

    //edit함수
    const editEventHandler=(e)=>{
        setItem({...item,title:e.target.value});        
    }

    // 엔터누르면 수정완료
    const turnOnReadOnly=(e)=>{
        if(e.key === "Enter" && readOnly === false){            
            setReadOnly(true);
            editItem(item);
        }
    }

    // 체크박스 업데이트
    const checkboxEventHandler = (e) =>{
        item.done = e.target.checked;
        editItem(item);
    }

    return (
        <ListItem>
            <Checkbox checked={item.done} onChange={checkboxEventHandler}/>
            <ListItemText>
                <InputBase
                    inputProps={{ "aria-label": "naked", readOnly: readOnly }}
                    onClick={turnOffReadOnly}
                    onKeyDown={turnOnReadOnly}
                    onChange={editEventHandler}
                    type="text"
                    id={item.id}
                    name={item.id}
                    value={item.title}
                    multiline={true}
                    fullWidth={true}
                />
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton aria-label="Delete Todo" onClick={deleteEventHandler}>
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    );
};

export default Todo;