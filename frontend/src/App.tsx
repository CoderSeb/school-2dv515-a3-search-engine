import { useState } from 'react'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import Table from 'react-bootstrap/Table'
import './App.css'

interface IResult {
  link: string,
  score: number,
  content: number,
  location: number,
  pageRank: number
}

function App() {
  const [results, setResults] = useState<IResult[]>()
  const [searchLevel, setSearchLevel] = useState<String>('LOW')
  const [searchQuery, setSearchQuery] = useState('')

  const handleSearch = () => {
    setResults([])
    fetch(`http://localhost:8080/api/search?query=${searchQuery}&searchLevel=${searchLevel}`)
      .then(res => res.json())
      .then(
        (result: IResult[]) => {
          setResults(result)
        }
      )
  }
  return (
    <div className="App">
      <h1>Assignment 3 - Search Engine</h1>
      <div className="searchContainer">
        <div className="searchInputContainer">
          <Form.Select className="searchLevelSelect" onChange={e => setSearchLevel(e.target.value)}>
            <option value="LOW">Grade E</option>
            <option value="MEDIUM">Grade C-D</option>
            <option value="HIGH">Grade A-B</option>
          </Form.Select>
          <Form.Control className="searchInput" type="text" defaultValue={searchQuery} onChange={e => setSearchQuery(e.target.value)} placeholder="Search..." />
          <Button className="searchButton" onClick={handleSearch}>Search!</Button>
        </div>

        <div className="searchResultContainer">
          <Table striped bordered hover variant="dark">
            <thead>
              <tr>
                <th className="linkField">Link</th>
                <th className="scoreField">Score</th>
                <th className="contentField">Content</th>
                <th className="locationField">Location</th>
                <th className="pageRankField">Page Rank</th>
              </tr>
            </thead>
            <tbody>
              {results && results.map((result, index) => (
                <tr key={index}>
                  <td className="linkField">{result.link}</td>
                  <td className="scoreField">{result.score.toFixed(2)}</td>
                  <td className="contentField">{result.content.toFixed(2)}</td>
                  <td className="locationField">{result.location.toFixed(2)}</td>
                  <td className="pageRankField">{result.pageRank.toFixed(2)}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    </div>
  )
}


export default App
