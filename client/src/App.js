import "./App.css"

import React, { useEffect, useState } from "react"
import "./App.css"
import InputItem from "./components/InputItem"
import TodoList from "./components/TodoList"

function App(props) {
    const [todoList, setTodoList] = useState([])

    useEffect(() => {
        console.log(">>>>> useEffect ...")
        const requestOptions = {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        }
        fetch("http://localhost:7788/todo/list", requestOptions)
            .then((response) => {
                return response.json()
            })
            .then(
                (response) => {
                    console.log(">>>> data : ", response.data)
                    setTodoList(response.data)
                },
                (err) => {
                    console.log(err)
                }
            )
    }, [])

    function restCRUD(method, data) {
        const requestOptions = {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        }
        fetch("http://localhost:7788/todo", requestOptions)
            .then((response) => response.json())
            .then(
                (response) => {
                    setTodoList(response.data)
                },
                (err) => {}
            )
    }

    function appendItem(newItem) {
        restCRUD("POST", { title: newItem })
    }
    function removeItem(item) {
        restCRUD("DELETE", item)
    }

    function updateItem(item) {
        restCRUD("PUT", item)
    }

    return (
        <>
            <h1>Todo List</h1>
            <InputItem appendItem={appendItem} />
            <hr />
            <TodoList
                todoList={todoList}
                removeItem={removeItem}
                updateItem={updateItem}
            />
        </>
    )
}

export default App
